import write from '../html/write.html';

const writeNode = () => {
  const node = document.createElement('div');
  node.innerHTML = write;

  // Event
  return node.children;
};

export default writeNode;
