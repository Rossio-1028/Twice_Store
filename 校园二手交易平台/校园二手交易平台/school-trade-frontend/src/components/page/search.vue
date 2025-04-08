<template>
    <div>
        <app-head :searchInput="searchValue"></app-head>
        <app-body>
            <div style="min-height: 85vh;">
                <div style="margin: 0 20px;padding-top: 20px;">
                    <div style="text-align: center;color: #555555;padding: 20px;" v-if="idleList.length===0">暂无匹配的二手物品</div>
                    <el-row :gutter="30">
                        <el-col :span="6" v-for="(idle,index) in idleList">
                            <div class="idle-card" @click="toDetails(idle)">
                                <el-image
                                        style="width: 100%; height: 160px"
                                        :src="idle.imgUrl"
                                        fit="contain">
                                    <div slot="error" class="image-slot">
                                        <i class="el-icon-picture-outline">无图</i>
                                    </div>
                                </el-image>
                                <div class="idle-title">
                                    {{idle.idleName}}
                                </div>
                                <el-row style="margin: 5px 10px;">
                                    <el-col :span="12">
                                        <div class="idle-prive">￥{{idle.idlePrice}}</div>
                                    </el-col>
                                    <el-col :span="12">
                                        <div class="idle-place">{{idle.idlePlace}}</div>
                                    </el-col>
                                </el-row>
                                <div class="idle-time">{{idle.timeStr}}</div>
                                <div class="user-info">
                                    <el-image
                                            style="width: 30px; height: 30px"
                                            :src="idle.user.avatar"
                                            fit="contain">
                                        <div slot="error" class="image-slot">
                                            <i class="el-icon-picture-outline">无图</i>
                                        </div>
                                    </el-image>
                                    <div class="user-nickname">{{idle.user.nickname}}</div>
                                </div>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <div class="fenye">
                    <el-pagination
                            background
                            @current-change="handleCurrentChange"
                            :current-page.sync="currentPage"
                            :page-size="8"
                            layout="prev, pager, next, jumper"
                            :total="totalItem">
                    </el-pagination>
                </div>
            </div>
            <app-foot></app-foot>
        </app-body>
    </div>
</template>

<script>
    import AppHead from '../common/AppHeader.vue';
    import AppBody from '../common/AppPageBody.vue'
    import AppFoot from '../common/AppFoot.vue'

    export default {
        name: "search",
        components: {
            AppHead,
            AppBody,
            AppFoot
        },
        data() {
            return {
                idleList: [],
                currentPage: 1,
                searchValue: '',
                totalItem:1
            };
        },
        //组件创建时执行的钩子函数
        created() {
            //调用搜索闲置商品的方法，初始页码为1
            this.findIdleTiem(1, this.$route.query.searchValue);
            //获取路由中的搜索值
            this.searchValue = this.$route.query.searchValue;
        },
        //监听路由变化
        watch: {
            $route(to, from) {
                //更新搜索值
                this.searchValue = to.query.searchValue;
                //调用搜索闲置商品的方法，根据新的页码和搜索值查询
                this.findIdleTiem(to.query.page, to.query.searchValue);
            }
        },
        methods: {
            //搜索闲置商品的方法
            findIdleTiem(page, findValue) {
                //调用API接口进行搜索
                this.$api.findIdleTiem({
                    page: page,
                    nums: 8,
                    findValue: findValue
                }).then(res => {
                    //打印响应结果
                    console.log(res);
                    //获取响应中的商品列表
                    let list = res.data.list;
                    //遍历商品列表，处理时间和图片信息
                    for (let i = 0; i < list.length; i++) {
                        //格式化发布时间
                        list[i].timeStr = list[i].releaseTime.substring(0, 10) + " " + list[i].releaseTime.substring(11, 19);
                        //解析图片列表
                        let pictureList = JSON.parse(list[i].pictureList);
                        //获取第一张图片的URL
                        list[i].imgUrl = pictureList.length > 0 ? pictureList[0] : '';
                    }
                    //更新闲置商品列表
                    this.idleList = list;
                    //更新总商品数量
                    this.totalItem=res.data.count;
                }).catch(e => {
                    //打印错误信息
                    console.log(e)
                })
            },
            //处理标签点击事件
            handleClick(tab, event) {
                console.log(tab, event);
                console.log(this.labelName)
            },
            //处理页码变化事件
            handleCurrentChange(val) {
                //打印当前页码
                console.log(`当前页: ${val}`);
                //替换路由，更新页码和搜索值
                this.$router.replace({query: {page: val, searchValue: this.searchValue}});
            },
            //跳转到详情页的方法
            toDetails(idle) {
                //跳转到详情页，并传递商品ID
                this.$router.push({path: '/details', query: {id: idle.id}});
            }
        }
    }
</script>

<style scoped>
    .idle-card {
        height: 300px;
        border: #eeeeee solid 1px;
        margin-bottom: 15px;
        cursor: pointer;
    }

    .fenye {
        display: flex;
        justify-content: center;
        height: 60px;
        align-items: center;
    }

    .idle-title {
        font-size: 18px;
        font-weight: 600;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        margin: 10px;
    }

    .idle-prive {
        font-size: 16px;
        color: red;
    }

    .idle-place {
        font-size: 13px;
        color: #666666;
        float: right;
        padding-right: 20px;

    }

    .idle-time {
        color: #666666;
        font-size: 12px;
        margin: 0 10px;
    }

    .user-nickname {
        color: #999999;
        font-size: 12px;
        display: flex;
        align-items: center;
        height: 30px;
        padding-left: 10px;
    }

    .user-info {
        padding: 5px 10px;
        height: 30px;
        display: flex;
    }
</style>