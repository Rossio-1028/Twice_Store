<template>
    <div class="login-container">
        <el-card class="box-card">
            <div class="login-body">
                <div class="login-title">
                    <img src="../../assets/syslogo.jpg" style="width: 150px;position: relative; top: 20px;right: 6px">
                </div>
                <el-form ref="form" :model="userForm">
                    <el-input placeholder="请输入管理员账号" v-model="userForm.accountNumber" class="login-input">
                        <template slot="prepend">
                            <div class="el-icon-user-solid"></div>
                        </template>
                    </el-input>
                    <el-input placeholder="请输入管理员密码" v-model="userForm.adminPassword" class="login-input"
                              @keyup.enter.native="login"  show-password>
                        <template slot="prepend">
                            <div class="el-icon-lock"></div>
                        </template>
                    </el-input>
                    <div class="login-submit" style="margin-top: 20px"  >
                        <el-button type="primary" @click="login">登录</el-button>
                        <el-button type="success" autocomplete="off" @click="$router.push('/login')" style="margin-left: 20px">前台登录</el-button>
                    </div>
                </el-form>
            </div>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "login",
        data() {
            return { //返回组件的数据对象
                userForm: {  //管理员表单数据对象，包括账号和密码
                    accountNumber: '',
                    adminPassword: ''
                }
            };
        },
        methods: {
            //登录方法
            login() {
                this.$api.adminLogin({  //调用API进行管理员登录，传递账号和密码
                    accountNumber: this.userForm.accountNumber,
                    adminPassword: this.userForm.adminPassword
                }).then(res => {
                    console.log(res); //打印响应信息
                    if (res.status_code === 1) { //判断后台是否发送请求成功
                        console.log(res);
                        this.$sta.isLogin = true;  //设置登录状态为已登录
                        this.$sta.adminName=res.data.adminName; //设置管理员名称
                        this.$router.replace({path:'/platform-admin'});  //跳转到管理员平台页面
                    } else {
                        //登录失败，弹出错误信息
                        this.$message.error('登录失败，账号或密码错误！');
                    }
                });

            }
        }
    }
</script>

<style scoped>
    .login-container {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        width: 100%;
        background-color: #f1f1f1;
        background: url("../../assets/login-back.png") center top / cover no-repeat;
    }
    .login-body {
        padding: 30px;
        width: 300px;
        height: 100%;
    }
    .login-title {
        padding-bottom: 30px;
        text-align: center;
        font-weight: 600;
        font-size: 20px;
        color: #409EFF;
        cursor: pointer;
    }
    .login-input {
        margin-bottom: 20px;
    }
    .login-submit {
        display: flex;
        justify-content: center;
    }
    .sign-in-text {
        color: #409EFF;
        font-size: 16px;
        text-decoration: none;
        line-height:28px;
    }
    .other-submit{
        display:flex;
        justify-content: space-between;
        margin-top: 10px;
    }
</style>
