import main from '../html/main.html';
import axios from 'axios';
import postFunc from './post';

const mainNode = () => {
  const node = document.createElement('div');
  node.innerHTML = main;
  if (window.location.pathname !== '/') window.history.pushState(null, null, '/');

  // Event
  postFunc.top3posts(node.querySelector('.top3-container'));

  return node.children;
};

export default mainNode;
