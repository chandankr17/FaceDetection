console.log('Starting...');

const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');

console.log('Modules loaded');

const app = express();
app.use(express.json({ limit: '10mb' }));
app.use(cors());

app.use('/auth', require('./routes/auth'));
app.use('/results', require('./routes/results'));

app.get('/', (req, res) => {
  res.send('FaceDetection Backend Running on port 3000 ✅');
});

mongoose.connect('mongodb://127.0.0.1:27017/facedetection')
  .then(() => {
    console.log('MongoDB connected!');
    app.listen(3000, () => {
      console.log('Server running on port 3000');
    });
  })
  .catch(err => {
    console.log('MongoDB error:', err.message);
  });