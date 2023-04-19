import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import VueSocketIO from 'vue-socket.io'
import ECharts from 'vue-echarts'

import 'echarts/lib/chart/line'
import 'echarts/lib/component/tooltip'
import 'echarts/lib/component/title'
import 'echarts/lib/component/legend'
import 'echarts/lib/component/dataZoom'

import Vant from 'vant';
import 'vant/lib/index.css';
 
Vue.use(Vant);

// import * as echarts from 'echarts/lib/echarts.js'
// import { TitleComponent } from 'echarts/components';
// echarts.use([TitleComponent]);
// import { LegendComponent } from 'echarts/components';
// echarts.use([LegendComponent]);
// import { TooltipComponent } from 'echarts/components';
// echarts.use([TooltipComponent]);
// import { GridComponent } from 'echarts/components';
// echarts.use([GridComponent]);
// import { DataZoomComponent } from 'echarts/components';
// echarts.use([DataZoomComponent]);
Vue.component('chart', ECharts)

// Vue.use(new VueSocketIO({
//   debug: false,
//   connection: 'http://localhost:3000',
//   vuex: {

//   }
// }))


Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
