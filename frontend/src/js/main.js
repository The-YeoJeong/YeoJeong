import main from '../html/main.html';
import axios from 'axios';
import postFunc from './post';
import detailFunc from './detail';
import post from './post';

const mainNode = () => {
  const node = document.createElement('div');
  node.innerHTML = main;
  if (window.location.pathname !== '/') window.history.pushState(null, null, '/');

  let regionName = '';
  let period = '';
  let postContent = '';
  let order = '';
  let searchContent = '';

  let currentPageNum = 0;
  let postCntPerPage = 5;
  let totalCnt = 0;

  var reqBody = {
    "regionName": [regionName],
    "postContent" : postContent,
    "order" : order,
    "period" : period,
    "searchContent" : searchContent
  }
  
  function getPosts(){
    console.log("getPosts : ------------------------ ");

    $.ajax({
      type: "post",
      contentType: 'application/json',
      data: JSON.stringify(reqBody),
      url: '/api/main/post?page=' + currentPageNum + '&size=' + postCntPerPage,
      dataType: 'json',
      async: false,
      processData: false,
      success: function (data) {
        console.log("postList result : " + JSON.stringify(data));

        // totalCnt = data.postCnt;
      }
    });
  };
 
  getPosts();



  // Event
  //검색
  node.querySelector('.GO-button').addEventListener('click', (e) => {
    e.preventDefault();
    getPosts()
  });









  postFunc.top3posts(node.querySelector('.top3-container'));
  // console.log(postFunc.mainPost());

  node.querySelector('.plan-city').addEventListener('click', e => {
    // e.target.style.backgroundColor = '#60b2ff';
    if (e.target.tagName === 'INPUT') {
      console.log(e.target.value);
      regionName = e.target.value;
      document.querySelector('#city-name').value = regionName;
    }
  });

  node.querySelector('.period-buttons').addEventListener('click', e => {
    if (e.target.tagName === 'INPUT') {
      console.log(e.target.tagName);
      period = e.target.dataset.id;
      document.querySelector('#travel-period').value = e.target.value;
    }
  });

  node.querySelector('.top3-container').addEventListener('click', e => {
    if (e.target.className.split('__')[0].includes('top-post')) {
      postFunc.detailPost(document.querySelector('.container'), e.target.dataset.id);
      postFunc.commentList(document.querySelector('.comment_container'), e.target.dataset.id);
      window.history.pushState(null, null, `detail/${e.target.closest('div').dataset.id}`);
    }
    console.log()
  });

  return node.children;
};

export default mainNode;
