import axios from 'axios';
//封装HTTP请求
const service = axios.create({
    timeout: 5000,
    //设置请求的基础URL，后续所有的请求都在这个基础上拼接
    baseURL:  'http://localhost:8080',
    //允许跨域请求时携带cookie等凭证信息
    withCredentials:  true
});

service.interceptors.request.use(
    config => {
        return config;
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

service.interceptors.response.use(
    response => {
        if (response.status === 200) {
            return response.data;
        } else {
            Promise.reject();
        }
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

export default service;
