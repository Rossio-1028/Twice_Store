const webpack = require('webpack')

module.exports = {
    publicPath: './',
    assetsDir: 'static',
    productionSourceMap: false,
    configureWebpack: {
        plugins: [
            new webpack.ProvidePlugin({
                $: "jquery",
                jQuery: "jquery",
                "windows.jQuery": "jquery"
            })
        ]
    },
    devServer: {
        port: 8080,
    },
    devServer: {
        proxy: {
            '/api':{
                target:'http://localhost:8080',
                changeOrigin:true,
                pathRewrite:{
                    '/api':'http://localhost:8080'
                }
            }
        }
    }
};