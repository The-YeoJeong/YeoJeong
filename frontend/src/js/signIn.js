// import axios from 'axios';
import signin from '../html/SignIn.html';

const signinNode = () => {
  const node = document.createElement('div');
  node.innerHTML = signin;

  node.querySelector('.button.signup').addEventListener('click', () => {
    window.history.pushState(null, null, '/signup');
  });

  console.log(node.querySelector('.signupbutton'));

  return node.children;
};

export default signinNode;
