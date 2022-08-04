import axios from 'axios';
import detail from '../html/Detail.html';
import postFunc from '../js/post';
import map from '../js/map';

const detailNode = () => {
  const uri = window.location.pathname.split('/');
  const postId = uri[uri.length - 1];
  const node = document.createElement('div');

  node.innerHTML = detail;
  postFunc.detailPost(node.querySelector('.card-container'), postId);

  postFunc.commentList(node.querySelector('.comment_container'), postId);

  node.querySelector('.fas.fa-heart').addEventListener('click', e => {
    e.target.classList.toggle('liked');
    number(node.querySelector('.post__likenum').textContent)
  });

  //지도 관련
  let detailMapContainer = node.querySelector('.detailMap'), // 지도를 표시할 div
    detailMapOption = {
      center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
      level: 1, // 지도의 확대 레벨
    };

  // map.makedetailMap(detailMapContainer, detailMapOption);


  return node.children;
};

export default detailNode;
