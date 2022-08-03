import mypage from '../html/Mypage.html';
import axios from 'axios';
import postFunc from './post';

const mypageNode = () => {
  const node = document.createElement('div');
  node.innerHTML = mypage;

  const resignMember = async () => {
    try {
      const { data } = await axios.delete(`/api/member/delete`, {
        headers: {
          Authorization: `Bearer ` + window.localStorage.getItem('jwt')
        }
      });
    } catch (e) {
      console.log(e);
    }
    window.localStorage.clear();
    window.history.pushState(null, null, '/');
  };


  // Event

  node.querySelector('.resign').addEventListener('click', () => {
    document.querySelector('.resign-modal').classList.toggle('hidden');
  });

  node.querySelector('.resign-confirm').addEventListener('click', resignMember);
  
  node.querySelector('.resign-cancle').addEventListener('click', () => {
    document.querySelector('.resign-modal').classList.toggle('hidden');
  });

  return node.children;
};

export default mypageNode;
