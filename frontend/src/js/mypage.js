import mypage from '../html/Mypage.html';
import axios from 'axios';
import postFunc from './post';

const mypageNode = () => {
  const node = document.createElement('div');
  node.innerHTML = mypage;

  // Event

  return node.children;
};

export default mypageNode;
