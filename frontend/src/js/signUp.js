import axios from 'axios';
import validatation from './validation';
import signup from '../html/SignUp.html';

const signupNode = () => {
  const node = document.createElement('div');
  node.innerHTML = signup;

  //func

  const checkId = async () => {
    const { status } = await axios.get(`/api/member/new/idCheck/${document.querySelector('#id').value}`);
    console.log(status);
  };

  const checkNick = nick => {};

  const signUp = e => {
    e.preventDefault();
    try {
    } catch (e) {
      console.log(e);
    }
  };

  //Event
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
