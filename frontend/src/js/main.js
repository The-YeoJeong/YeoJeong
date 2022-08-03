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
    // e.target.style.backgroundColor = '#60b2ff';
    if (e.target.tagName === 'INPUT') {
      console.log(e.target.value);
      regionName = e.target.value;
      document.querySelector('#city-name').value = regionName;
    }
  });

  node.querySelector('.period-buttons').addEventListener('click', e => {
    if (e.target.tagName === 'BUTTON') {
      // e.target이 뭔지?
      // e.target에서 input에 접근하기
      //checked 값이 ture인지 확인하고
      //true이면 style 변경
      console.log(e.target.tagName);
      period = e.target.dataset.id;

      document.querySelector('#travel-period').value = e.target.value;
    }
  });

  return node.children;
};

export default mainNode;
