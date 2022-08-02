import axios from 'axios';
import header from '../html/header.html';

const createHeaderNode = async () => {
  const node = document.createElement('div');
  node.innerHTML = header;

  const { data } = await axios.get(`/api/member/get/me`, {
    headers: {
      Authorization: `Bearer ` + window.localStorage.getItem('jwt')
    }
  });

  if (window.localStorage.getItem('jwt') != null) { // 로그인시
    // 기록하기, 000님, 홈 아이콘, 로그아웃
    node.querySelector('.users__nickname').textContent = data.memberNickname;
    node.querySelector('.users__signin').style.display = "none";
    node.querySelector('.users__signup').style.display = "none";

  } else { // 로그아웃시
    // 로그인, 회원가입
    node.querySelector('.write-button').style.display = "none";
    node.querySelector('.nim').style.display = "none";
    node.querySelector('.fas.fa-house').style.display = "none";
    node.querySelector('.users__logout').style.display = "none";
  }

  node.querySelector('.header').addEventListener('click', e => {
    if (e.target.classList.contains('maininfo')) {
      window.history.pushState(null, null, '/');
    } else if (e.target.classList.contains('write-button')) {
      window.history.pushState(null, null, '/write');
    } else if (e.target.classList.contains('users__signin')) {
      window.history.pushState(null, null, '/signin');
    } else if (e.target.classList.contains('users__signup')) {
      window.history.pushState(null, null, '/signup');
    } else if (e.target.classList.contains('fa-house')) {
      window.history.pushState(null, null, '/mypage');
    }
  });

  // 로그아웃
  node.querySelector('.users__logout').addEventListener('click', () => {
    window.localStorage.clear();
    window.history.pushState(null, null, '/');
  });

  return node.children;
};

export default createHeaderNode;