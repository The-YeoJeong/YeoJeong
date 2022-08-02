import App from './js/app';
import createHeaderNode from './js/header';

const history = require('history-events');

const $root = document.getElementById('root');

const render = async elem => {
  const headerNode = await createHeaderNode();

  const path = window.location.pathname.split('/');
  if (path[1] === 'signin' || path[1] === 'signup' || path[1] === 'oauthSignUp') {
    $root.replaceChildren(...elem);
  } else {
    $root.replaceChildren(...headerNode, ...elem);
  }
};
render(App());

// url 변경감지
window.addEventListener('changestate', () => {
  render(App());
});
