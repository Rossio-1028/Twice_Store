<template>
    <div class="login-container" >
        <el-card class="box-card">
            <div class="login-body">
                <div class="login-title" @click="toIndex" >
                    <img src="../../assets/syslogo.jpg" style="width: 150px;position: relative; top: 20px;right: 6px">
                </div>
                <el-form ref="form" :model="userForm">
                    <el-input placeholder="请输入账号..." v-model="userForm.accountNumber" class="login-input">
                        <template slot="prepend">
                            <div class="el-icon-user-solid"></div>
                        </template>
                    </el-input>
                    <el-input placeholder="请输入密码..." v-model="userForm.userPassword" class="login-input"
                              @keyup.enter.native="login"  show-password>
                        <template slot="prepend">
                            <div class="el-icon-lock"></div>
                        </template>
                    </el-input>
                    <div class="login-submit" >
                        <el-button type="primary" @click="login">登录</el-button>
                        <el-button type="warning" autocomplete="off" @click="$router.push('/sign-in')" style="margin-left: 20px">注册</el-button>
                        <el-button type="success" autocomplete="off" @click="$router.push('/login-admin')" style="margin-left: 20px">管理员登录</el-button>

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
            return {
                userForm: {
                // 用户表单数据，包括账号和密码
                    accountNumber: '',
                    userPassword: ''
                }
            };
        },

        methods: {
            login() {
                // 调用API进行用户登录，传递账号和密码
                this.$api.userLogin({
                    accountNumber: this.userForm.accountNumber,
                    userPassword: this.userForm.userPassword
                }).then(res => {
                    // 打印结果
                    console.log(res);

                    // 判断是否登录成功
                    if (res.status_code === 1) {
                        // 为了对登录时间进行处理，截取登录时间的前10位
                        res.data.signInTime=res.data.signInTime.substring(0,10);
                        // 将用户数据存储到全局数据中
                        this.$globalData.userInfo = res.data;
                        // 跳转到首页
                        this.$router.replace({path: '/index'});
                    } else {
                        // 登录失败，提示错误信息
                        this.$message.error(res.msg);
                    }
                });
            },
            // 点击logo 跳转首页
            toIndex() {
                this.$router.replace({path: '/index'});
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
        background: url("../../assets/login-back.png") center top / cover no-repeat;
    }

    .login-body {
        padding: 30px;
        width: 300px;
        height: 100%;
        opacity: 0.9;
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
        margin-top: 20px;
        display: flex;
        justify-content: center;
    }

    .sign-in-container {
        padding: 0 10px;
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
        margin-left: 0px;
    }
</style>
