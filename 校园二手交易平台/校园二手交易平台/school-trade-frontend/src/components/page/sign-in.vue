<template>
    <div class="sign-in-container">
        <el-card class="box-card">
            <div class="sign-in-body">
                <div class="sign-in-title">
                    <img src="../../assets/syslogo.jpg" style="height: 140px;width: 140px;position: relative; top: 8px;right: 6px">
                    <br>
                    <span style='color: #e75c09'>账号注册</span>
                </div>
                <el-input placeholder="请输入昵称..." maxlength="30"  v-model="userInfo.nickname" class="sign-in-input" clearable>
                    <template slot="prepend">
                        <div class="el-icon-user-solid"></div>
                    </template>
                </el-input>
                <el-input placeholder="请输入帐号..." maxlength="11" v-model="userInfo.accountNumber" class="sign-in-input" clearable>
                    <template slot="prepend">
                        <div class="el-icon-phone"></div>
                    </template>
                </el-input>
                <el-input placeholder="请输入密码..." show-password maxlength="16" v-model="userInfo.userPassword" class="sign-in-input" clearable>
                    <template slot="prepend">
                        <div class="el-icon-lock"></div>
                    </template>
                </el-input>
                <el-input placeholder="请再次输入密码..." show-password maxlength="16" v-model="userPassword2" @keyup.enter.native="signIn" class="sign-in-input" clearable>
                    <template slot="prepend">
                        <div class="el-icon-lock"></div>
                    </template>
                </el-input>
                <div class="sign-in-submit">
                    <el-button type="primary" @click="signIn">提交</el-button>
                    <el-button type="primary" @click="toLogin" style="margin-left: 20px" >返回登录</el-button>
                </div>

            </div>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "sign-in",
        data(){
            return{
                //第二次输入的密码
                userPassword2:'',
                userInfo:{   //用户信息对象（账号、密码、昵称）
                    accountNumber:'',
                    userPassword:'',
                    nickname:''
                }
            };
        },
        methods:{
            //跳转到登录页面的方法
            toLogin(){
                //使用Vue Router替换当前路由为登录页面
                this.$router.replace({path: '/login'});
            },
            //注册方法
            signIn(){
                console.log(this.userInfo.nickname); //打印用户输入的昵称
                //检查用户是否填写了所有必要信息
                if(this.userInfo.accountNumber&&this.userInfo.userPassword&&this.userInfo.nickname){
                    //检查两次输入的密码是否一致
                    if(this.userInfo.userPassword!==this.userPassword2){
                        //不一致，显示错误信息
                        this.$message.error('两次输入的密码不相同！');
                    }else {
                        //调用API进行注册
                        this.$api.signIn(this.userInfo).then(res=>{
                            //如果后端返回成功请求
                            if(res.status_code===1){
                                //显示成功信息并跳转到登录页面
                                this.$message({
                                    message: '注册成功！',
                                    type: 'success'
                                });
                                this.$router.replace({path: '/login'});
                            }else {
                                //如果注册失败（用户已存在），显示错误信息
                                this.$message.error('注册失败，用户已存在！');
                            }
                        }).catch(e=>{
                            //若网络异常，显示错误信息并打印错误信息
                            console.log(e);
                            this.$message.error('注册失败，网络异常！');
                        })
                    }
                }else{
                    //若信息未填写完整，显示错误信息
                    this.$message.error('注册信息未填写完整！');
                }
            }
        }
    }
</script>

<style scoped>
    .sign-in-container {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        width: 100%;
        background-color: #f1f1f1;
        background: url("../../assets/login-back.png") center top / cover no-repeat;
    }

    .sign-in-body {
        padding: 30px;
        width: 300px;
        height: 100%;
    }

    .sign-in-title {
        padding-bottom: 30px;
        text-align: center;
        font-weight: 600;
        font-size: 20px;
        color: #409EFF;
    }

    .sign-in-input {
        margin-bottom: 20px;
    }
    .sign-in-submit{
        margin-top: 20px;
        display: flex;
        justify-content: center;
    }
    .login-container{
        padding: 0 10px;
    }
    .login-text{
        color: #409EFF;
        font-size: 16px;
        cursor:pointer;
    }
</style>
