import axios from 'axios';
import signin from '../html/SignIn.html';

const signinNode = () => {
  const node = document.createElement('div');
  node.innerHTML = signin;

  return node.children;
};

export default signinNode;
