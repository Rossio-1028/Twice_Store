<template>
    <div style="background-color: #f6f6f6;min-height:100vh;">
        <el-container>
            <el-header>
                <div class="header">
                    <div class="app-name">
                        <router-link to="/platform-admin" >
                            <span style='color: rgba(67,52,166,0.71)'>后台管理</span>
                        </router-link>
                    </div>
                    <span class="app-title">{{admin.nickname}}</span>
                    <div  class="app-logOut">
                        <el-button style="margin-right: 100px" type="primary" @click="logout">退出登录</el-button>
                    </div>
                </div>
            </el-header>
            <el-container>
                <div class="mainBody" background-color="#3a8ee6">
                    <el-aside >
                        <el-col :span="24" >
                            <el-menu
                                    default-active="1"
                                    class="el-menu-vertical-demo"
                                    @select="handleSelect"
                                    background-color="dodgerblue"
                                    text-color="#fff"
                                    active-text-color="#fff">
                                <el-menu-item index="1">
                                    <i class="el-icon-goods"></i>
                                    <span>商品管理</span>
                                </el-menu-item>
                                <el-menu-item index="2">
                                    <i class="el-icon-s-goods"></i>
                                    <span slot="title">订单管理</span>
                                </el-menu-item>
                                <el-menu-item index="3" >
                                    <i class="el-icon-s-custom"></i>
                                    <span slot="title">用户管理</span>
                                </el-menu-item>
                            </el-menu>
                        </el-col>
                    </el-aside>
                    <el-main>
                        <IdleGoods v-if="mode == 1"></IdleGoods>
                        <orderList v-if="mode == 2"></orderList>
                        <userList v-if="mode == 3"></userList>
                    </el-main>
                </div>
            </el-container>
        </el-container>
        <div class="foot">
            <app-foot></app-foot>
        </div>
    </div>
</template>

<script>
    import AppFoot from '../common/AppFoot.vue'
    import IdleGoods from '../common/IdleGoods.vue'
    import orderList from '../common/orderList.vue'
    import userList from '../common/userList.vue'
    export default {
        name: "platform-admin",
        components: {
            AppFoot,
            IdleGoods,
            orderList,
            userList,
        },
        data(){
            return {
                mode:1, //当前显示的模块，初始值为1（商品管理）
                admin:{ //管理员信息，包括昵称
                    nickname:'管理员',
                },
            }
        },
        created() { //在组件创建时，从全局状态中获取管理员昵称
            this.admin.nickname=this.$sta.adminName;
        },
        methods: {  //退出登录（管理员）
            logout(){ 
                //调用退出登录接口
                this.$api.loginOut({
                }).then(res => {
                    //退出成功，更新全局状态
                    if(res.status_code === 1){
                        this.$sta.isLogin=false;
                        this.$sta.adminName='';
                        //跳转到管理员登录页面
                        this.$router.push({path:'/login-admin'});
                    }
                }).catch(e => {
                    console.log(e) //捕获错误并打印到控制台
                })
            },
            handleSelect(val){
                //当前菜单项被选中时，更新mode值
                if(this.mode !== val){
                    this.mode = val
                }
            },
        },
    }
</script>

<style scoped>
    .header {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        min-width: 100vw;
        height: 58px;
        background: #efe8e8;
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-bottom: #eeeeee solid 2px;
        z-index: 1000;
    }
    .app-name {
        display: flex;
        justify-content: center;
        align-items: center;
        min-width: 10vw;
        flex: 1;
        height: 100%;
        border-right:1px solid #e5e5e5;
        background-color: #e5e5e5;

    }
    .app-name a {
        color: #efe8e8;
        font-size: 18px;
        font-weight: 800;
        text-decoration: none;
        padding:0 20px;
    }
    .app-title {
        display: flex;
        justify-content: center;
        flex: 8;
    }
    .app-logOut{
        display:flex;
        flex: 1;
        justify-content: flex-end;
        align-items: center;
    }
    .mainBody {
        display: flex;
        width: 100%;
    }
    aside {
        flex: 1;
        box-sizing:content-box;
        min-width: 10vw;
        min-height:calc(100vh - 120px);
        background-color: rgb(255, 255, 255);
        border-bottom: 1px solid #e5e5e5;
        border-right: 1px solid #e5e5e5;

    }
    main {
        flex: 9;
    }
    .foot {
        position: absolute;
        left: 0;
        bottom: 0;
        width: 100%;
        height: 58px;
        background-color: #ffffff;
    }
</style>
