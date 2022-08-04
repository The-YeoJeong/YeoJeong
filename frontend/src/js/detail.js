import axios from 'axios';
import detail from '../html/Detail.html';
import postFunc from '../js/post';

const detailNode = () => {
  console.log('하하하하ㅏㅎ');
  const uri = window.location.pathname.split('/');
  const postId = uri[uri.length - 1];
  const node = document.createElement('div');
  console.log(postId);

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
    }, error: function (request, status, error) {
      console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
    }
  });

  node.innerHTML = detail;

  postFunc.detailPost(node.querySelector('.card-container'), postId, user.memberId);

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
  });

  return node.children;
};

export default detailNode;
