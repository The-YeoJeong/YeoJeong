import mypage from '../html/Mypage.html';
import axios from 'axios';
import postFunc from './post';

const mypageNode = () => {
  const node = document.createElement('div');
  node.innerHTML = mypage;

  // Event

  node.querySelector('.resign').addEventListener('click', () => {
    document.querySelector('.resign-modal').classList.toggle('hidden');
  });

  node.querySelector('.resign-cancle').addEventListener('click', () => {
    document.querySelector('.resign-modal').classList.toggle('hidden');
  });
  return node.children;
};

export default mypageNode;
