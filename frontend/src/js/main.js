import main from '../html/main.html';
import axios from 'axios';
import postFunc from './post';

const mainNode = () => {
  const node = document.createElement('div');
  node.innerHTML = main;
  if (window.location.pathname !== '/') window.history.pushState(null, null, '/');

  let regionName = '';
  let period = '';
  let postContent = '';
  let order = '';
  let searchContent = '';

  // Event
  postFunc.top3posts(node.querySelector('.top3-container'));

  node.querySelector('.plan-city').addEventListener('click', e => {
    e.target.style.backgroundColor = '#60b2ff';
    regionName = e.target.textContent;
    document.querySelector('#city-name').value = regionName;
  });

  node.querySelector('.period-buttons').addEventListener('click', e => {
    if (e.target.tagName === 'BUTTON') {
      e.target.style.border = '3px solid black';
      period = e.target.dataset.id;
      document.querySelector('#travel-period').value = e.target.textContent;
    }
  });

  return node.children;
};

export default mainNode;
