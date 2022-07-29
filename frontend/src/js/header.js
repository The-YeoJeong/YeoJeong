import axios from 'axios';
import header from '../html/header.html';

const createHeaderNode = async () => {
  const node = document.createElement('div');
  node.innerHTML = header;

  node.querySelector('.header').addEventListener('click', e => {
    if (e.target.classList.contains('maininfo')) {
      window.history.pushState(null, null, '/');
    } else if (e.target.classList.contains('write-button')) {
      window.history.pushState(null, null, '/write');
    } else if (e.target.classList.contains('users__signin')) {
      window.history.pushState(null, null, '/signin');
    } else if (e.target.classList.contains('users__signup')) {
      window.history.pushState(null, null, '/signup');
    }
  });

  //로그아웃이 되어야 함
  // node.querySelector('.users--logout').addEventListener('click', () => {
  //   window.history.pushState(null, null, '/signin');
  // });

  return node.children;
};

export default createHeaderNode;
