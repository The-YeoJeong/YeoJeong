import write from '../html/write.html';
import map from './map';
import post from './post';

const writeNode = () => {
  const node = document.createElement('div');

  node.innerHTML = write;
  // Event
  const $cardContainer = node.querySelector('.card-container');
  const $postTitle = node.querySelector('#post-title').value;

  let postTitle = '';
  let postStartDate = '';
  let postEndDate = '';
  let postRegionName = [];
  let postDateCard = [
    { postDateCardTitle: '', postScheduleCard: [{ placeName: '', placeAddress: '', placeContent: '' }] },
  ];
  let postContent = '';
  let postOnlyMe = false;

  //일자 카드 추가
  node.querySelector('.add-date-card-button').addEventListener('click', () => {
    post.addDataCard($cardContainer);
  });

  $cardContainer.addEventListener('click', e => {
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

  //작성 완료
  node.querySelector('.post-button').addEventListener('click', () => {});
  //지도 관련
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
    map.searchPlaces();
  });

  node.querySelector('#close').addEventListener('click', map.close);

  //후기 관련
  node.querySelector('.review-button').addEventListener('click', e => {
    if (e.target.textContent === '후기 작성 폼 열기') e.target.textContent = '후기 작성 폼 닫기';
    else e.target.textContent = '후기 작성 폼 열기';

    document.querySelector('.review-container').classList.toggle('hidden');
  });

  function sendFile(file, el) {
    var form_data = new FormData();
    form_data.append('file', file);
    $.ajax({
      data: form_data,
      type: 'POST',
      url: '/api/image',
      cache: false,
      contentType: false,
      enctype: 'multipart/form-data',
      processData: false,
      success: function (url) {
        $(el).summernote('insertImage', url, function ($image) {
          $image.css('width', '50%');
        });
      },
    });
  }

  //기타
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
    $('#plan-period-startdate').datepicker({
      dateFormat: 'yy-mm-dd',
      // 날짜의 형식
      changeMonth: true,
      // 월을 이동하기 위한 선택상자 표시여부
      minDate: 0,
      // 선택할수있는 최소날짜, ( 0 : 오늘 이전 날짜 선택 불가)
      onClose: function (selectedDate) {
        // 시작일(fromDate) datepicker가 닫힐때
        // 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
        $('#plan-period-enddate').datepicker('option', 'minDate', selectedDate);
      },
    });
    //종료일
    $('#plan-period-enddate').datepicker({
      dateFormat: 'yy-mm-dd',
      changeMonth: true,
      minDate: 0,
      // 오늘 이전 날짜 선택 불가
      onClose: function (selectedDate) {
        // 종료일(toDate) datepicker가 닫힐때
        // 시작일(fromDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정
        $('#plan-period-startdate').datepicker('option', 'maxDate', selectedDate);
      },
    });
  });

  return node.children;
};

export default writeNode;