import axios from 'axios';
import detail from '../html/Detail.html';
import postFunc from '../js/post';

const detailNode = () => {
  const uri = window.location.pathname.split('/');
  const postId = uri[uri.length - 1];

  const node = document.createElement('div');
  node.innerHTML = detail;
  postFunc.detailPost(node.querySelector('.card-container'), postId);

  postFunc.commentList(node.querySelector('.comment_container'), postId);
  return node.children;
};

export default detailNode;
