import edit from '../html/Edit.html';
import postFunc from '../js/post';
import map from '../js/map';

const editNode = () => {
  const uri = window.location.pathname.split('/');
  const postId = uri[uri.length - 1];

  const node = document.createElement('div');
  node.innerHTML = edit;

  postFunc.editPostRender(node.querySelector('.editcard-container'), postId);

  const $cardContainer = node.querySelector('.editcard-container');

  $cardContainer.addEventListener('click', e => {
    console.log('fdafs');
    //일정카드 추가
    if (e.target.className === 'add-schedule-button') {
      const { previousElementSibling } = e.target.parentNode;
      post.addScheduleCard(previousElementSibling);
    }
    //일정, 일자 카드 삭제
    if (e.target.classList.contains('fa-xmark')) {
      e.target.parentNode.parentNode.remove();
    }
    //주소 검색
    if (e.target.id === 'location__addr') {
      map.setInputArea(e.target, e.target.previousElementSibling.previousElementSibling);
      show();
    }
  });

  //지도
  let mapContainer = node.querySelector('.map'), // 지도를 표시할 div
    mapOption = {
      center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
      level: 3, // 지도의 확대 레벨
    };

  map.makeMap(mapContainer, mapOption);

  function show() {
    document.querySelector('.background').className = 'background show';
  }

  node.querySelector('.searchLoca').addEventListener('click', () => {
    console.log(e.target);
    map.searchPlaces();
  });

  node.querySelector('#close').addEventListener('click', map.close);

  $(function () {
    $('#summernote').summernote({
      height: 300,
      minHeight: null,
      maxHeight: null,
      focus: true,
      callbacks: {
        onImageUpload: function (files, editor, welEditable) {
          for (var i = 0; i < files.length; i++) {
            sendFile(files[i], this);
          }
        },
      },
      toolbar: [
        ['font', ['underline', 'clear']],
        ['fontname', ['fontname']],
        ['color', ['color']],
        ['para', ['paragraph']],
        ['height', ['height']],
        ['table', ['table']],
        ['insert', ['link', 'picture', 'hr']],
        ['view', ['codeview']],
        ['help', ['help']],
      ],
    });

    $('#editpostplan-period-startdate').datepicker({
      dateFormat: 'yy-mm-dd',
      // 날짜의 형식
      changeMonth: true,
      // 월을 이동하기 위한 선택상자 표시여부
      minDate: 0,
      // 선택할수있는 최소날짜, ( 0 : 오늘 이전 날짜 선택 불가)
      onClose: function (selectedDate) {
        // 시작일(fromDate) datepicker가 닫힐때
        // 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
        $('#editpostplan-period-enddate').datepicker('option', 'minDate', selectedDate);
      },
    });
    //종료일
    $('#editpostplan-period-enddate').datepicker({
      dateFormat: 'yy-mm-dd',
      changeMonth: true,
      minDate: 0,
      // 오늘 이전 날짜 선택 불가
      onClose: function (selectedDate) {
        // 종료일(toDate) datepicker가 닫힐때
        // 시작일(fromDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정
        $('#editpostplan-period-startdate').datepicker('option', 'maxDate', selectedDate);
      },
    });
  });

  return node.children;
};

export default editNode;
