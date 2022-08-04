import axios from 'axios';
import detail from '../html/Detail.html';
import postFunc from '../js/post';
import map from '../js/map';

const detailNode = () => {
  const uri = window.location.pathname.split('/');
  const postId = uri[uri.length - 1];
  const node = document.createElement('div');

  let user = '';

  $.ajax({
    type: 'GET',
    url: '/api/member/get/me',
    headers: { Authorization: `Bearer ` + window.localStorage.getItem('jwt') },
    timeout: 5000,
    dataType: 'json',
    async: false,
    cache: false,
    success: function (data) {
      user = data;
    },
    error: function (request, status, error) {
      console.log('code:' + request.status + '\n' + 'message:' + request.responseText + '\n' + 'error:' + error);
    },
  });

  node.innerHTML = detail;

  const $commentContainer = node.querySelector('.comment_container');

  if (user) {
    node.querySelector('.comment-wrapper').classList.remove('hidden');
  }

  postFunc.detailPostRender(node.querySelector('.card-container'), postId, user.memberId);

  postFunc.commentRender($commentContainer, postId, user.memberId);

  const like = async () => {
    const { status } = await axios({
      method: 'post',
      url: '/api/heart/new',
      headers: { Authorization: `Bearer ` + window.localStorage.getItem('jwt') },
      data: { postNo: postId },
    });
    console.log(status);
  };

  const dislike = async () => {
    const { status } = await axios({
      method: 'delete',
      url: '/api/heart/delete',
      headers: { Authorization: `Bearer ` + window.localStorage.getItem('jwt') },
      data: { postNo: postId },
    });
    console.log(status);
  };

  node.querySelector('.fas.fa-heart').addEventListener('click', e => {
    if (window.localStorage.getItem('jwt') != null) {
      if (e.target.classList.contains('liked')) {
        e.target.classList.remove('liked');
        let postLikedNum = Number(document.querySelector('.post__likenum').textContent);
        postLikedNum -= 1;
        document.querySelector('.post__likenum').textContent = postLikedNum;
        dislike();
      } else {
        e.target.classList.add('liked');
        let postLikedNum = Number(document.querySelector('.post__likenum').textContent);
        postLikedNum += 1;
        document.querySelector('.post__likenum').textContent = postLikedNum;
        like();
      }
    }
  });

  const wirteComment = async comment => {
    const { data } = await axios({
      method: 'post',
      url: '/api/comment/new',
      headers: { Authorization: `Bearer ` + window.localStorage.getItem('jwt') },
      data: { postNo: postId, commentContent: comment },
    });
    const newComment = `<div class="post-comment">
    <span class="comment-writer">${data.comment.memberNickname}(${data.comment.memberId})</span>
    <span class="comment-content">${data.comment.commentContent}</span>
    <span class="comment-date">${data.comment.createdTime.substring(0, 10)}</span>
    <div class="comment-buttons">
    <button class = "comment-editBtn">수정</button>
    <button class = "comment-deleteBtn">삭제</button>
    </div>
    </div>`;
    return newComment;
  };

  const updateComment = async (commentNo, comment) => {
    console.log(commentNo, comment);
    const { status } = await axios({
      method: 'patch',
      url: `/api/comment/edit/${commentNo}`,
      headers: { Authorization: `Bearer ` + window.localStorage.getItem('jwt') },
      data: { postNo: postId, commentContent: comment },
    });
    console.log('댓글 수정', status);
  };

  const deleteComment = async commentNo => {
    const { status } = await axios({
      method: 'delete',
      url: `/api/comment/${commentNo}`,
      headers: { Authorization: `Bearer ` + window.localStorage.getItem('jwt') },
    });
    console.log('댓글 삭제', status);
  };

  node.querySelector('.comment-button').addEventListener('click', () => {
    wirteComment(document.querySelector('#comment').value).then(res => {
      $commentContainer.innerHTML += res;
    });
  });

  node.querySelector('.comment_container').addEventListener('click', e => {
    if (e.target.className === 'comment-deleteBtn') {
      e.target.parentNode.parentNode.remove();
      deleteComment(e.target.parentNode.parentNode.dataset.id);
    }
    if (e.target.className === 'comment-editBtn') {
      e.target.classList.add('hidden');
      document.querySelector('.comment-editingBtn').classList.remove('hidden');
      e.target.parentNode.parentNode.querySelector('.comment-content').disabled = false;
    }
    if (e.target.className === 'comment-editingBtn') {
      e.target.classList.add('hidden');
      document.querySelector('.comment-editBtn').classList.remove('hidden');
      e.target.parentNode.parentNode.querySelector('.comment-content').disabled = true;
      updateComment(
        e.target.parentNode.parentNode.dataset.id,
        e.target.parentNode.parentNode.querySelector('.comment-content').value
      );
    }
  });
  //지도 관련
  let detailMapContainer = node.querySelector('.detailMap'), // 지도를 표시할 div
    detailMapOption = {
      center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
      level: 1, // 지도의 확대 레벨
    };

  // map.makedetailMap(detailMapContainer, detailMapOption);//dkd

  node.querySelector('.delete').addEventListener('click', () => {
    if (confirm('정말 삭제하시겠습니까 ?') == true) {
      console.log('postNo :@@@@ ' + postId);
      $.ajax({
        type: 'delete',
        url: '/api/post/' + postId,
        headers: { Authorization: `Bearer ` + window.localStorage.getItem('jwt') },
        timeout: 5000,
        dataType: 'json',
        async: false,
        cache: false,
        success: function () {},
      });
      alert('삭제되었습니다');
      window.history.pushState(null, null, '/');
    } else {
      return;
    }
  });

  return node.children;
};

export default detailNode;
