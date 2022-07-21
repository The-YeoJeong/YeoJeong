import axios from 'axios';
import signup from '../html/SignUp.html';

const signupNode = () => {
  const node = document.createElement('div');
  node.innerHTML = signup;

  return node.children;
};

export default signupNode;
