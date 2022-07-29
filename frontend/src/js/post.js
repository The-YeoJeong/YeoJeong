import axios from 'axios';

const top3posts = async container => {
  const { data } = await axios.get('/api/main/post/top');
  console.log(data);
};

export default {
  top3posts,
};
