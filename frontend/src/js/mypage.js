import mypage from '../html/Mypage.html';
import axios from 'axios';
import postFunc from './post';

// mypage에 접근하면 myPosts가 defalut로 실행 -> 여행기록(section이 전역으로 0으로 설정)
// section = 0인 상태에서 검색을 하면 search()의 section이 0인 상태에서 전역으로 searchContent가 설정됨
// 이 상태에서 계획 filter나 페이징을 하면 그정보 또한 전역으로 설정

const mypageNode = () => {
  const node = document.createElement('div');
  node.innerHTML = mypage;

  const $pagenationArea = node.querySelector('#pagination');

  let posts;
  let sectionValue;

  let planFilterValue = 0;
  let searchContentValue = '';


  //pagenation
  let currentPageNum = 1;
  let totalCnt = 0;
  let postCntPerPage = 5;
  let totalPageNum = 0;
  let pageGroup = 1;

  let start = 0;
  let end = 0;

  myPosts()

  function init() {
    planFilterValue = 0;
    searchContentValue = '';
    currentPageNum = '';

    document.querySelector('#search').value = "";
    document.querySelector('.control__sort').value = -1;
  };

  // 여행 기록 post 가져오기
  function myPosts() {
    sectionValue = 0;
    getPosts()
  };


  function getPosts() {
    console.log("getPosts : ------------------------ ");
    console.log("sectionValue : " + sectionValue);
    console.log("searchContentValue : " + searchContentValue);
    console.log("planFilterValue : " + planFilterValue);
    console.log("currentPageNum : " + currentPageNum);
    console.log("postCntPerPage : " + postCntPerPage);

    $.ajax({
      type: "GET",
      url: '/api/mypage/post/' + sectionValue + '/filter?searchContent=' + searchContentValue + '&onlyPlan=' + planFilterValue + '&page=' + currentPageNum + '&size=' + postCntPerPage,
      headers: { "Authorization": `Bearer ` + window.localStorage.getItem('jwt') },
      timeout: 5000,
      dataType: 'json',
      async: false,
      cache: false,
      success: function (data) {
        console.log("postCnt result@@@ : " + data.postCnt);
        console.log("postList result1 : " + data.postList);
        console.log("postList result2 : " + JSON.stringify(data));
        posts = data;

        totalCnt = data.postCnt;
        // totalCnt = 65;
        // totalPageNum = Math.ceil(totalCnt / 5);
      }
    });

    // totalCnt = 65;
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
  };

  const resignMember = async () => {
    try {
      const { data } = await axios.delete(`/api/member/delete`, {
        headers: {
          Authorization: `Bearer ` + window.localStorage.getItem('jwt')
        }
      });
    } catch (e) {
      console.log(e);
    }
    window.localStorage.clear();
    window.history.pushState(null, null, '/');
  };


  // posts를 그리는 함수 필요

  // Event

  // 여행 기록
  node.querySelector('[data-id="0"]').addEventListener('click', () => {
    window.history.pushState(null, null, '/mypage');
  });

  // 좋아요한 글
  node.querySelector('[data-id="1"]').addEventListener('click', () => {
    sectionValue = 1;
    init()
    document.querySelector('.control__category').textContent = "좋아요한 글";
    document.querySelector('.control__sort').style.display = "none";
    getPosts()
  });

  // 댓글 단 글 
  node.querySelector('[data-id="2"]').addEventListener('click', () => {
    sectionValue = 2;
    init()
    document.querySelector('.control__category').textContent = "답글 단 글";
    document.querySelector('.control__sort').style.display = "none";
    getPosts()
  });

  //검색
  node.querySelector('.search-button').addEventListener('click', (e) => {
    e.preventDefault();
    searchContentValue = document.querySelector('#search').value;
    getPosts()
  });

  // 계획 필터
  node.querySelector('.control__sort').addEventListener('click', (e) => {
    if (e.target.value == 0 || e.target.value == 1) {
      planFilterValue = e.target.value;
      getPosts()
    }
  });

  // 페이징 버튼 클릭
  node.querySelector('#pagination').addEventListener('click', e => {
    if (e.target.classList.contains('page-next')) {
      console.log(e.target)
      pageGroup += 1;
      getPosts()
    }
  })

  node.querySelector('#pagination').addEventListener('click', e => {
    if (e.target.classList.contains('page-prev')) {
      console.log(e.target)
      pageGroup -= 1;
      getPosts()
    }
  })

  node.querySelector('#pagination').addEventListener('click', e => {
    if (e.target.classList.contains('page-idx')) {
      console.log("*****************************")
      // console.log(e.target.textContent)
      currentPageNum = e.target.textContent;
      console.log("*****************************")
      // console.log(currentPageNum)
      getPosts()
    }
  })


  node.querySelector('.resign').addEventListener('click', () => {
    document.querySelector('.resign-modal').classList.toggle('hidden');
  });

  node.querySelector('.resign-confirm').addEventListener('click', resignMember);

  node.querySelector('.resign-cancle').addEventListener('click', () => {
    document.querySelector('.resign-modal').classList.toggle('hidden');
  });


  return node.children;
};

export default mypageNode;
