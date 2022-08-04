import main from '../html/main.html';
import axios from 'axios';
import postFunc from './post';
import detailFunc from './detail';
import post from './post';

const mainNode = () => {
  const node = document.createElement('div');
  node.innerHTML = main;
  const $pagenationArea = node.querySelector('#pagination');

  if (window.location.pathname !== '/') window.history.pushState(null, null, '/');

  let regionName = '전국';
  let period = -1;
  let postContent = false;
  let order = false;
  let searchContent = '';

  let currentPageNum = 0;
  let postCntPerPage = 5;
  let totalCnt = 0;
  let totalPageNum = 0;
  let pageGroup = 1;

  let start = 0;
  let end = 0;

  var reqBody = {
    regionName: [regionName],
    postContent: postContent,
    order: order,
    period: period,
    searchContent: searchContent,
  };

  function getPosts() {
    console.log('getPosts : ------------------------ ');
    console.log('regionName : ' + regionName);
    console.log('period : ' + period);
    console.log('postContent : ' + postContent);
    console.log('order : ' + order);
    console.log('searchContent : ' + searchContent);

    reqBody = {
      regionName: [regionName],
      postContent: postContent,
      order: order,
      period: period,
      searchContent: searchContent,
    };

    $.ajax({
      type: 'post',
      contentType: 'application/json',
      data: JSON.stringify(reqBody),
      url: '/api/main/post?page=' + (currentPageNum - 1) + '&size=' + postCntPerPage,
      dataType: 'json',
      async: false,
      processData: false,
      success: function (data) {
        console.log("postList result : " + JSON.stringify(data));
        console.log("postList type : " + typeof(data));
        totalCnt = data.postCnt;
        // totalCnt = data.postCnt;
      },
    });

    totalPageNum = Math.ceil(totalCnt / 5);

    let pagenation = '';

    start = pageGroup * 5 - 4;

    if (totalPageNum < pageGroup * 5) {
      end = totalPageNum;
    } else {
      end = pageGroup * 5;
    }

    if (pageGroup != 1) {
      pagenation += `<span class="page-prev"> < </span>`;
    }
    for (let i = start; i <= end; i++) {
      pagenation += `<span class="page-idx"> ${i} </span>`;
    }
    if (totalPageNum > pageGroup * 5) {
      pagenation += `<span class="page-next"> > </span>`;
    }

    $pagenationArea.innerHTML = pagenation;
  }

  getPosts();

  // Event
  // 고 버튼
  node.querySelector('.GO-button').addEventListener('click', e => {
    e.preventDefault();
    getPosts();
  });

  // 정렬
  node.querySelector('.option').addEventListener('click', e => {
    if (e.target.value == 0) {
      order = false;
      getPosts();
    } else if (e.target.value == 1) {
      order = true;
      getPosts();
    }
  });

  // 후기가 포함된 글만 포기(null)
  node.querySelector('.withreview').addEventListener('click', e => {
    if (e.target.checked) {
      postContent = true;
    } else {
      postContent = false;
    }
    getPosts();
  });

  //검색
  node.querySelector('.search-button').addEventListener('click', e => {
    e.preventDefault();
    searchContent = document.querySelector('#search').value;
    getPosts();
  });

  // 페이징 버튼 클릭
  node.querySelector('#pagination').addEventListener('click', e => {
    if (e.target.classList.contains('page-next')) {
      console.log(e.target);
      pageGroup += 1;
      getPosts();
    }
  });

  node.querySelector('#pagination').addEventListener('click', e => {
    if (e.target.classList.contains('page-prev')) {
      console.log(e.target);
      pageGroup -= 1;
      getPosts();
    }
  });

  node.querySelector('#pagination').addEventListener('click', e => {
    if (e.target.classList.contains('page-idx')) {
      console.log('*****************************');
      // console.log(e.target.textContent)
      currentPageNum = e.target.textContent;
      console.log('*****************************');
      // console.log(currentPageNum)
      getPosts();
    }
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
      // postFunc.commentList(document.querySelector('.comment_container'), e.target.dataset.id);

      window.history.pushState(null, null, `detail/${e.target.closest('div').dataset.id}`);
    }
  });

  return node.children;
};

export default mainNode;
