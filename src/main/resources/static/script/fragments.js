const mall = "/mall";
const puching = "/puching";
const member = "/member";
const board = "/board";
const admin	= "/admin";
let $_csrf;
let $_csrf_input;
let $viewportMetaTag;


$(document).ready(async function() {
        $_csrf = $('meta[name="_csrf"]').attr('content');
        $_csrf_input = $('<input type="hidden" name="_csrf" value="' + $_csrf + '">');
        $viewportMetaTag = $('meta[name="viewport"]');
        if (/Mobi/i.test(navigator.userAgent)) { // 모바일 기기인 경우
            $viewportMetaTag.attr('content', 'width=1200, initial-scale=0.4');
        }
        let csrf = $('#_csrf');
        if (csrf.length) {
            csrf.val($_csrf);
        }
        const url = location.host;
    if (isLogin) {
        let notificationSocket;
        printAside();
        try {
            const result = await $.ajax("/api/v1/notification/aside/list");
            printBsideNotification(result);
        } catch (err) {
            console.log(err)
        }

        // Bside
        if (url.includes("namohagae.kro.kr")) {
            notificationSocket = new WebSocket('wss://namohagae.kro.kr/notification');
        } else if (url.includes("localhost:8081")) {
            notificationSocket = new WebSocket('ws://localhost:8081/notification');
        }

        notificationSocket.addEventListener('open', function (event) {
            // 연결이 성공한 경우 실행되는 코드
            console.log('Notification Service On');
        });

        notificationSocket.addEventListener('message', function (event) {
            // 메시지를 받은 경우 실행되는 코드
            const data = event.data;
            const notification = JSON.parse(data).notification;
            let $li = $(`<li><a data-notification-no="${notification.notificationNo}" id="read" href="${notification.notificationLink}">${notification.notificationContent}</a></li>`);
            $li.css('background-color', '#c9dc92');
            $('#notification').prepend($li);
            setTimeout(function () {
                $li.css('background-color', '');
            }, 2000);
        });

        notificationSocket.addEventListener('close', function (event) {
            console.log('Notification Service Off');
            // 연결이 종료된 경우 실행되는 코드
        });

        notificationSocket.addEventListener('error', function (event) {
            console.log('Notification Service Error');
            // 에러가 발생한 경우 실행되는 코드
        });
        $('#notification').on("click", "#read", async function () {
            const $notificationNo = $(this).data('notification-no');
            try {
                await $.ajax({
                    url: "/api/v1/notification/read?notificationNo=" + $notificationNo + "&_csrf=" + $_csrf,
                    type: "PUT"
                });
                // 읽음 여부를 업데이트한 후의 작업을 수행
            } catch (err) {
                console.log(err);
            }
        })
        let currentPosition = parseInt($(".quickMenu").css("top"));
        $(window).scroll(function () {
            const position = $(window).scrollTop();
            $(".quickMenu").stop().animate({"top": position + currentPosition + "px"}, 1000);
        });


        // Header
        $("#logout").click(function () {
            // body.append(form) -> body,  form.appendTo(body) -> form
            $('<form>').append($_csrf_input).attr("action", "/logout").attr("method", "post")
                .appendTo($('body')).submit();
        })
        $('.header_logo').click(function () {
            location.href = '/';
        })
    }
})

function printAside(){
    let sideBar = location.pathname;
    const $title = $('#aside_title');
    const $side_bar = $('#side_bar');
    $title.text("");
    $side_bar.empty();
    if(sideBar.startsWith(mall)){
        $title.append('<li><a href="/mall/main">뼈다귀몰</a></li>');
        $side_bar.append('<li><a href="/mall/product/list?categoryNo=1">사료/간식</a></li>');
        $side_bar.append('<li><a href="/mall/product/list?categoryNo=2">장난감</a></li>');
        $side_bar.append('<li><a href="/mall/product/list?categoryNo=3">산책용품</a></li>');
        $side_bar.append('<li><a href="/mall/product/list?categoryNo=4">기타</a></li>');
    }else if(sideBar.startsWith(member)){
        $title.append('<li><a href="/member/information">내 정보</a></li>');
        $side_bar.append('<li><a href="/member/notification">알림함</a></li>');
        $side_bar.append('<li><a href="/member/puching/review">내가 작성한 리뷰</a></li>');
        $side_bar.append('<li><a href="/member/follow">팔로우 목록</a></li>');
        $side_bar.append('<li><a href="/member/mall/address">배송지</a></li>');
        $side_bar.append('<li><a href="/member/mall/favorite">찜 목록</a></li>');
        $side_bar.append('<li><a href="/member/mall/order">주문 내역</a></li>');
        $side_bar.append('<li><a href="/member/mall/qna">상품 문의내역</a></li>');
        $side_bar.append('<li><a href="/member/mall/review">상품 리뷰</a></li>');
        $side_bar.append('<li><a href="/member/board/post">내가 작성한 게시물</a></li>');
        $side_bar.append('<li><a href="/member/board/comment">내가 작성한 댓글</a></li>');
        $side_bar.append('<li><a href="/member/board/question">내가 작성한 질문</a></li>');
        $side_bar.append('<li><a href="/member/board/answer">내가 작성한 답변</a></li>');
    }else if(sideBar.startsWith(puching)){
        $title.append('<li><a href="/puching/main">퍼칭</a></li>');
        $side_bar.append('<li><a href="/puching/chatroom">채팅방</a></li>');
        $side_bar.append('<li><a href="/puching/puching_introduce">퍼칭소개</a></li>');
        $side_bar.append('<li><a href="/puching/search">상대찾기</a></li>');
    }else if(sideBar.startsWith(board)){
        $title.append('<li><a href="/board/main">커뮤니티</a></li>');
        $side_bar.append('<li><a href="/board/notice/list">공지사항</a></li>');
        $side_bar.append('<li><a href="/board/free/list">자유 게시판</a></li>');
        $side_bar.append('<li><a href="/board/town/list">동네 게시판</a></li>');
        $side_bar.append('<li><a href="/board/knowledge/list">지식인</a></li>');
    }else if(sideBar.startsWith(admin)){
        $title.append('<li><a href="/admin/main">관리자</a></li>');
        $side_bar.append('<li><a href="/admin/notice/list">공지사항</a></li>');
        $side_bar.append('<li><a href="/admin/town/list">동네 등록</a></li>');
        $side_bar.append('<li><a href="/admin/qna/list">QnA</a></li>');
        $side_bar.append('<li><a href="/admin/product/list">상품 목록</a></li>');
        $side_bar.append('<li><a href="/admin/report/report">신고 관리</a></li>');
    }


    window.addEventListener('load', function() {
        const currentUrl = location.pathname.split("?");
        const links = document.getElementsByTagName('a');

        for (let i = 0; i < links.length; i++) {
            const link = links[i];

            // <section> 요소와 그 자손 요소를 건너뛰기
            if (link.closest('section') !== null) {
                continue;
            }

            if (link.pathname.split("?")[0] === "/mall/product/list") {
                if (link.href === location.href) {
                    console.log(location.href);
                    link.style.color = '#007bff';
                }
            } else if (link.pathname.split("?")[0] === currentUrl[0]) {
                link.style.color = '#007bff';
            }
        }
    });
}

function printBsideNotification(result) {
    const $notification = $('#notification');
    result.forEach(n => {
        const tpl = `
            		 <li><a href="${n.notificationLink}" id="read" data-notification-no="${n.notificationNo}">${n.notificationContent}</a></li>
       			 `;
        $notification.append(tpl);
    });
}



