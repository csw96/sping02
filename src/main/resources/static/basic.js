let targetId;

$(document).ready(function () {
    if ($.cookie('token')) {
        $.ajaxSetup({
            headers: {
                'Authorization': $.cookie('token')
            }
        })
    }
    $.ajax({
        type: "POST",
        url: `/user/userinfo`,
        contentType: "application/json",
        success: function (response) {
            const username = response.username;
            if (username) {
                $('#username').text(username + "님의");
                $('#login-page').hide();
                $('#my_form').show();
                $('#content_form').show();
            } else {
                $('#username').text("로그인 전입니다.");
                $('#login-page').show();
                $('#my_form').hide();
                $('#content_form').hide();
            }

        },
        error: function () {
            $('#username').text("로그인 전입니다.");
            $('#login-page').show();
            $('#my_form').hide();
            $('#content_form').hide();
        }
    })
    $('#update-area').hide();
    $('#goBack').hide();
    showContents();
})

function longinCheck() {
    $.ajax({
        type: "POST",
        url: `/user/userinfo`,
        contentType: "application/json",
        success: function (response) {
            let username = response.username;
            if (!username) {
                alert("로그인이 필요합니다.")
                window.location.reload()
            }
        },
        error: function () {
            alert("로그인이 필요합니다.");
            window.location.reload()
        }
    })
}


function showContents() {

    $.ajax({
        type: 'GET',
        url: '/api/contents',
        success: function (response) {
            $('#contents-container').empty();
            $('#comments-container').empty();
            for (let i = 0; i < response.length; i++) {
                let contents = response[i];
                let id = contents.id;
                let content = contents.contents;
                let username = contents.username;
                let modifiedAt = contents.modifiedAt;
                let tempHtml = addContentsList(username, content, id, modifiedAt);
                $('#contents-container').append(tempHtml);


            }
        },
        error: function () {
            $('#contents-container').empty();
        }
    })
}

function addContentsList(username, content, id, modifiedAt) {
    return `<div class="box">
                                <article class="mybox">
                                    <div class="content" >
                                            <p onclick='viewcontents(${id})' >
                                                <strong>${content}</strong> 
                                                <small>${username}</small> 
                                                <small>${modifiedAt}</small>
                                            </p>
                                            <div>
                                                <input type="text" id="${id}-updateContent" placeholder="수정내용">
                                            </div>
                                            <p>
                                                <img src="images/edit.png" onclick="editContent(${id});">
                                                <img src="images/delete.png" onclick="deleteContent(${id});">
                                            </p>
                                    </div>
                                </article>
                            </div>`;
}


function viewcontents(id) {
    $.ajax({
        type: "GET",
        url: `/api/contents/${id}`,
        success: function (response) {
            $('#contents-container').empty();
            let username = response.username;
            let content = response.contents;
            let modifiedAt = response.modifiedAt;
            let tempHtml = addContent(username, content, modifiedAt, id);
            $('#contents-container').append(tempHtml);
            $('#goBack').show();
            $('#content_form').hide();
        }
    })
    getComment(id);
}

function showPopup() {
    $('#update-area').show();
    $('#container').addClass('active');
}

function hidePopup() {
    $('#update-area').hide();
    $('#container').removeClass('active');
}

function addContent(username, content, modifiedAt, id) {
    return `<div class="box">
                                <article class="mybox">
                                    <div class="content" >
                                            <p>
                                                <strong>${content}</strong> <small>${username}</small> <small>${modifiedAt}</small>
                                            </p>
                                            <p>
                                                <img src="images/send.png" onclick='$("#modal-comment").addClass("is-active")'>
                                            </p>
                                    </div>
                                </article>
                            </div>
                                   <div class="modal" id="modal-comment">
                                        <div class="modal-background" onclick='$("#modal-comment").removeClass("is-active")'></div>
                                        <div class="modal-content">
                                            <div class="box">
                                                <article class="media">
                                                    <div class="media-content"></div>
                                                    <div id="contents-field" class="field">
                                                    <p id="view" class="control"></p>
                                                    </div>
                                                        <nav class="level is-mobile">
                                                            <div class="level-left">
                                                                <input class="input" id="${id}-comment" type="text" placeholder="댓글내용">
                                                            </div>
                                                            <div class="level-right">
                                                                <div class="level-item">
                                                                    <a class="button is-sparta" onclick="postComment(${id})">등록 하기</a>
                                                                </div>
                                                              
                                                                <div class="level-item">
                                                                    <a class="button is-sparta is-outlined"
                                                                       onclick='$("#modal-comment").removeClass("is-active")'>목록으로</a>
                                                                </div>
                                                            </div>
                                                        </nav>
                                                    </div>
                                                </article>
                                            </div>
                                        <button class="modal-close is-large" aria-label="close"
                                                onclick='$("#modal-comment").removeClass("is-active")'></button>
                                        </div>`;
}

function postContent() {
    longinCheck();
    let contents = $('#mycontent').val();
    let data = {'contents': contents};

    $.ajax({
        type: "POST",
        url: `/api/contents`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert(response)
            window.location.reload()
        }
    })
}


function editContent(id) {
    longinCheck();
    let contents = $(`#${id}-updateContent`).val();
    let data = {'contents': contents};
    $.ajax({
        type: "PUT",
        url: `/api/contents/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert(response)
            window.location.reload()
        }
    })
}


function deleteContent(id) {
    longinCheck();
    $.ajax({
        type: "DELETE",
        url: `/api/contents/${id}`,
        success: function (response) {
            alert(response)
            window.location.reload()
        }
    })
}



function postComment(id) {
    longinCheck();
    let comment = $(`#${id}-comment`).val();
    let data = {'comment': comment};
    $.ajax({
        type: "POST",
        url: `/api/comment/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert(response)
            getComment(id)
            $("#modal-comment").removeClass("is-active")
            let el = document.getElementsByClassName('input');
            for(let i=0; i<el.length; i++) {
                el[i].value = '';
            }

        }
    })
}

function getComment(id) {
    $.ajax({
        type: "GET",
        url: `/api/comment/${id}`,
        success: function (response) {
            $('#comments-container').empty();
            for (let i = 0; i < response.length; i++) {
                let comments = response[i];
                let commentId = comments.id;
                let username = comments.username;
                let comment = comments.comment;
                let modifiedAt = comments.modifiedAt;
                let tempHtml = addComment(username, comment, modifiedAt, commentId, id);
                $('#comments-container').append(tempHtml);
            }
        }
    })
}

function addComment(username, comment, modifiedAt, commentid, id) {
    return `<div class="box">
                                <article class="mybox">
                                    <div class="content" >
                                            <p>
                                                <strong>${comment}</strong> 
                                                <small>${username}</small> 
                                                <small>${modifiedAt}</small>
                                            </p>
                                            <div>
                                                <input type="text" id="${commentid}-updateComment" placeholder="수정내용">
                                            </div>
                                            <p>
                                                <img src="images/edit.png" onclick="editComment(${commentid},${id});">
                                                <img src="images/delete.png" onclick="deleteComment(${commentid},${id});">
                                            </p>
                                    </div>
                                </article>
                            </div>`;
}

function editComment(id, contentId) {
    longinCheck();
    let comment = $(`#${id}-updateComment`).val();
    let data = {'comment': comment};
    $.ajax({
        type: "PUT",
        url: `/api/comment/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert(response)
            getComment(contentId);
        }
    })
}


function deleteComment(id, contentId) {
    longinCheck();
    $.ajax({
        type: "DELETE",
        url: `/api/comment/${id}`,
        success: function (response) {
            alert(response)
            getComment(contentId);
        }
    })
}