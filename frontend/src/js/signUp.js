import axios from 'axios';
import validatation from './validation';
import signup from '../html/SignUp.html';

const signupNode = () => {
  const node = document.createElement('div');
  node.innerHTML = signup;

  //func

  // const checkId = async () => {
  //   const { result: isDuplicate } = await axios.get(`/api/member/new/idCheck/${document.querySelector('#id').value}`);
  //   validatation.isIdDuple(isDuplicate);
  // };

  // const checkNick = async () => {
  //   const { result: isDuplicate } = await axios.get(
  //     `/api/member/new/nickCheck/${document.querySelector('#nickname').value}`
  //     );
  //     validatation.isnickDup(isDuplicate);

  // };

  const checkId = async () => {
    try {
      const { status } = await axios.get(`/api/member/new/idCheck/${document.querySelector('#id').value}`);
      if (status === 200) {
        validatation.isIdDuple(true);
      } else {
        validatation.isIdDuple(false);
      }
    } catch (e) {
      console.log(e);
    }
  };

  const checkNick = async () => {
    try {
      const { status } = await axios.get(`/api/member/new/nickCheck/${document.querySelector('#nickname').value}`);
      if (status === 200) {
        validatation.isnickDup(true);
      } else {
        validatation.isnickDup(false);
      }
    } catch (e) {
      console.log(e);
    }
  };

  const signUp = async e => {
    e.preventDefault();
    try {
      const { status } = await axios.post('/api/member/new', {
        memberId: document.querySelector('#id').value,
        memberNickname: document.querySelector('#nickname').value,
        memberPw: document.querySelector('#password').value,
      });
      if (status === 200) {
        window.history.pushState(null, null, '/signin');
      }
    } catch (e) {
      console.log(e);
    }
  };

  //Event

  node.querySelector('.sign-header').addEventListener('click', () => {
    window.history.pushState(null, null, '/');
  });

  node.querySelector('.signup-wrapper').addEventListener('change', e => {
    if (e.target === document.querySelector('#id')) validatation.idValidate(e.target.value);
    if (e.target === document.querySelector('#password'))
      validatation.pwdValidate(e.target.value, document.querySelector('#passwordConfirm').value);
    if (e.target === document.querySelector('#passwordConfirm'))
      validatation.pwdValidate(document.querySelector('#password').value, e.target.value);
    if (e.target === document.querySelector('#nickname')) validatation.nickValidate(e.target.value);
  });

  node.querySelector('.button.dupl_id').addEventListener('click', checkId);
  node.querySelector('.button.dupl_nick').addEventListener('click', checkNick);
  node.querySelector('.button.signup-button').addEventListener('click', signUp);

  return node.children;
};

export default signupNode;
