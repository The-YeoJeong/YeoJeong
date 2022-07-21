import axios from 'axios';
import validatation from './validation';
import signup from '../html/SignUp.html';

const signupNode = () => {
  const node = document.createElement('div');
  node.innerHTML = signup;

  node.querySelector('.signup-wrapper').addEventListener('change', e => {
    if (e.target === document.querySelector('#id')) validatation.idValidate(e.target.value);
    if (e.target === document.querySelector('#password'))
      validatation.pwdValidate(e.target.value, document.querySelector('#passwordConfirm').value);
    if (e.target === document.querySelector('#passwordConfirm'))
      validatation.pwdValidate(document.querySelector('#password').value, e.target.value);
    if (e.target === document.querySelector('#nickname')) validatation.nickValidate(e.target.value);
  });

  return node.children;
};

export default signupNode;
