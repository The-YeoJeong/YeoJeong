import axios from 'axios';
import detail from '../html/Detail.html';
import postFunc from '../js/post';
import map from '../js/map';

const detailNode = () => {
  const uri = window.location.pathname.split('/');
  const postId = uri[uri.length - 1];
  const node = document.createElement('div');
  console.log(postId);

  let user = '';

  (async () => {
    const { data } = await axios.get('/api/member/get/me', {
      headers: { Authorization: `Bearer ` + window.localStorage.getItem('jwt') },
    });
    user = data;
    console.log(user);
    console.log(user.memberNickname);
  })();

  console.log(user.memberNickname);
  node.innerHTML = detail;

  postFunc.detailPost(node.querySelector('.card-container'), postId, user.memberNickname);

  postFunc.commentRender(node.querySelector('.comment_container'), postId);

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
      method: 'post',
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

  //지도 관련
  let detailMapContainer = node.querySelector('.detailMap'), // 지도를 표시할 div
    detailMapOption = {
      center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
      level: 1, // 지도의 확대 레벨
    };

  // map.makedetailMap(detailMapContainer, detailMapOption);//dkd

  return node.children;
};

export default detailNode;
