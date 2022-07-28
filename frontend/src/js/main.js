import main from '../html/main.html';

const mainNode = () => {
  const node = document.createElement('div');
  node.innerHTML = main;
  if (window.location.pathname !== '/') window.history.pushState(null, null, '/');

  // Event

  return node.children;
};

export default mainNode;
