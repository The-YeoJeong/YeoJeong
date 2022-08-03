import axios from 'axios';
import signin from '../html/SignIn.html';

const signinNode = () => {
  const node = document.createElement('div');
  node.innerHTML = signin;

  const signIn = async e => {
    e.preventDefault();
    try {
      const { data } = await axios.post('/api/member/login', {
        memberId: document.querySelector('#id').value,
        memberPw: document.querySelector('#password').value,
      });
      window.localStorage.setItem('jwt', data.jwt);
      window.history.pushState(null, null, '/');
    } catch (e) {
      document.querySelector('.error-msg.login').classList.remove('hidden');
      console.log(e);
    }
  };

  const naver = async e => {
    e.preventDefault();
    try {
      window.location.href =
        'https://nid.naver.com/oauth2.0/authorize?client_id=KWUoslendQDxZRSrkOFh&response_type=code&redirect_uri=http://localhost:4000/oauth2/naver/login&state=gnu5rdc4nnlq3pp67f32j38jv1';

      window.addEventListener('changestate', () => {
        console.log(window.location.pathname);
      });
    } catch (e) {
      console.log(e);
    }
  };

  const kakao = async e => {
    e.preventDefault();
    try {
      window.location.href =
        'https://kauth.kakao.com/oauth/authorize?client_id=d173137e871d96ad298bf43551057b2a&redirect_uri=http://localhost:4000/oauth2/kakao/login&response_type=code';

      window.addEventListener('changestate', () => {
        console.log(window.location.pathname);
      });
    } catch (e) {
      console.log(e);
    }
  };

  const google = async e => {
    e.preventDefault();
    try {
      window.location.href =
        'https://accounts.google.com/o/oauth2/v2/auth?client_id=950587226040-s9t5g9hpm48nkrr6tn4duovb249ltoee.apps.googleusercontent.com&redirect_uri=http://localhost:4000/oauth2/google/login&response_type=code&scope=email&access_type=offline';

      window.addEventListener('changestate', () => {
        console.log(window.location.pathname);
      });
    } catch (e) {
      console.log(e);
    }
  };

  node.querySelector('.sign-header').addEventListener('click', () => {
    window.history.pushState(null, null, '/');
  });

  node.querySelector('.signin-form').addEventListener('change', e => {
    let keyCode = e.keyCode;
    if (
      keyCode !== 32 &&
      document.querySelector('#id').value.trim() !== '' &&
      document.querySelector('#password').value.trim() !== ''
    ) {
      document.querySelector('.button.signin').disabled = false;
    } else {
      document.querySelector('.button.signin').disabled = true;
    }
  });

  node.querySelector('.button.signin').addEventListener('click', signIn);

  node.querySelector('.button.signup').addEventListener('click', () => {
    window.history.pushState(null, null, '/signup');
  });

  console.log(node.querySelector('.signupbutton'));

  node.querySelector('.social__login.naver').addEventListener('click', naver);
  node.querySelector('.social__login.kakao').addEventListener('click', kakao);
  node.querySelector('.social__login.google').addEventListener('click', google);

  return node.children;
};

export default signinNode;
