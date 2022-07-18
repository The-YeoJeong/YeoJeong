import main from '../html/main.html';

const mainNode = () => {
  const node = document.createElement('div');
  node.innerHTML = main;
  if (window.location.pathname !== '/') window.history.pushState(null, null, '/');

  // Event
  node.querySelector('.testBtn').addEventListener('click', () => console.log('click'));

  return node.children;
};

export default mainNode;
