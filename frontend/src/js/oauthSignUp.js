import axios from 'axios';
import validatation from './validation';
import OauthSignup from '../html/oauthSignUp.html';

const oauthSignUpNode = () => {
  const node = document.createElement('div');
  node.innerHTML = OauthSignup;

  const oauthCheckId = async () => {
    try {
      const { data } = await axios.get(`/api/member/new/idCheck/${document.querySelector('#id').value}`);
      validatation.isOAuthIdDuple(data.result);
    } catch (e) {
      console.log(e);
    }
  };

  const oauthCheckNick = async () => {
    try {
      const { data } = await axios.get(`/api/member/new/nickCheck/${document.querySelector('#nickname').value}`);
      validatation.isOAuthNickDup(data.result);
    } catch (e) {
      console.log(e);
    }
  };

  const signUp = async e => {
    e.preventDefault();
    try {
      const { data } = await axios.post(`/api/oauth2/` + window.localStorage.getItem('oauthCheck') + '/new?accessToken=' + window.localStorage.getItem('accessToken'), {
        memberId: document.querySelector('#id').value,
        memberNickname: document.querySelector('#nickname').value
      })

      if (data.jwt != null) {
        window.localStorage.setItem('jwt', data.jwt)
        window.history.pushState(null, null, '/')
      };
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
    if (e.target === document.querySelector('#nickname')) validatation.nickValidate(e.target.value);
  });

  node.querySelector('.button.dupl_id').addEventListener('click', oauthCheckId);
  node.querySelector('.button.dupl_nick').addEventListener('click', oauthCheckNick);
  node.querySelector('.button.signup-button').addEventListener('click', signUp);

  return node.children;
};

export default oauthSignUpNode;
