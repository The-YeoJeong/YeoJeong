import App from './js/app';
import createHeaderNode from './js/header';

const history = require('history-events');

const $root = document.getElementById('root');

// const render = async elem => {
//   const headerNode = await createHeaderNode();
//   $root.replaceChildren(...headerNode, ...elem);
// };

const render = async elem => {
  $root.replaceChildren(...elem);
};

render(App());

// url 변경감지
window.addEventListener('changestate', () => {
  render(App());
});
