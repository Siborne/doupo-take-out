import { createI18n } from 'vue-i18n'

const messages = {
  zh: {
    nav: {
      features: '功能特色',
      tech: '技术架构',
      screenshots: '界面预览',
      quickstart: '快速开始',
      github: 'GitHub'
    },
    hero: {
      badge: '开源餐饮外卖解决方案',
      title1: '智能餐饮',
      title2: '外卖系统',
      desc: '一站式餐饮外卖解决方案，支持微信小程序点餐、后台管理、实时数据统计',
      cta1: '快速开始 →',
      cta2: '查看源码',
      stat1: '核心端',
      stat2: '功能模块',
      stat3: '技术栈'
    },
    features: {
      badge: '功能特色',
      title: '为外卖而生的完整方案',
      desc: '完整的外卖业务闭环，从前端点餐到后端管理，一站式解决方案',
      items: [
        { title: '微信小程序点餐', desc: '用户通过微信授权登录，浏览菜单、加入购物车、在线支付，一键下单' },
        { title: '管理后台系统', desc: '员工管理、分类管理、菜品管理、套餐管理、订单处理，全方位运营支持' },
        { title: '数据统计分析', desc: 'ECharts 可视化报表，营业额、用户数量、订单趋势一目了然' },
        { title: '实时消息推送', desc: 'WebSocket 实时来单提醒、催单通知，不错过任何一笔订单' },
        { title: '安全认证体系', desc: 'JWT Token 认证授权，员工权限分级管理，数据安全保障' },
        { title: '云端文件存储', desc: '阿里云 OSS 对象存储，菜品图片、报表文件安全可靠' }
      ]
    },
    tech: {
      badge: '技术架构',
      title: '主流技术栈构建',
      desc: '前后端分离架构，可扩展性强，稳定可靠',
      items: [
        { name: 'Vue 3', desc: '前端框架' },
        { name: 'TypeScript', desc: '类型安全' },
        { name: 'Element UI', desc: 'UI 组件库' },
        { name: 'ECharts', desc: '数据可视化' },
        { name: 'Spring Boot', desc: '后端框架' },
        { name: 'MyBatis', desc: 'ORM 框架' },
        { name: 'MySQL', desc: '关系型数据库' },
        { name: 'Redis', desc: '缓存数据库' },
        { name: 'WebSocket', desc: '实时通信' },
        { name: 'JWT', desc: '身份认证' },
        { name: 'Nginx', desc: '反向代理' },
        { name: 'UniApp', desc: '小程序框架' }
      ],
      arch: {
        user: '用户端',
        userDesc: '微信小程序',
        admin: '管理端',
        adminDesc: 'Vue 3 + Element UI',
        server: '服务端',
        serverDesc: 'Spring Boot',
        data: '数据层',
        dataDesc: 'MySQL + Redis'
      }
    },
    screenshots: {
      badge: '界面预览',
      title: '简洁高效的管理界面',
      desc: '精心设计的管理后台，操作体验流畅自然',
      items: [
        { name: '工作台' },
        { name: '数据视图' },
        { name: '订单管理' },
        { name: '套餐管理' },
        { name: '菜品管理' },
        { name: '分类管理' },
        { name: '新增菜品' },
        { name: '员工管理' },
        { name: '店铺状态' }
      ]
    },
    quickstart: {
      badge: '快速开始',
      title: '几步启动项目',
      desc: '简单几步，快速运行整个系统',
      steps: [
        { title: '克隆项目', code: 'git clone https://github.com/Siborne/doupo-take-out.git' },
        { title: '配置数据库', code: '# 执行数据库初始化脚本\n# 配置 application-dev.yml' },
        { title: '启动后端服务', code: '# 进入 background/doupo-take-out 目录\nmvn spring-boot:run' },
        { title: '启动前端项目', code: '# 进入 front/project-doupo-admin-vue-ts 目录\nnpm install\nnpm run dev' }
      ],
      swagger: '访问 Swagger 文档验证服务状态'
    },
    cta: {
      title: '开始使用斗破快送',
      desc: '开源、免费、可定制的餐饮外卖解决方案',
      github: 'Star on GitHub',
      docs: '查看文档'
    },
    footer: {
      desc: '智能餐饮外卖系统\n一站式解决方案',
      features: '功能',
      tech: '技术',
      resources: '资源',
      items: {
        features: ['微信小程序点餐', '管理后台系统', '数据统计分析'],
        tech: ['Vue 3 + TypeScript', 'Spring Boot', 'MySQL + Redis'],
        resources: ['GitHub', '快速开始', '界面预览']
      }
    }
  },
  en: {
    nav: {
      features: 'Features',
      tech: 'Tech Stack',
      screenshots: 'Screenshots',
      quickstart: 'Quick Start',
      github: 'GitHub'
    },
    hero: {
      badge: 'Open Source Food Delivery Solution',
      title1: 'Smart Food',
      title2: 'Delivery System',
      desc: 'All-in-one food delivery solution with WeChat Mini Program ordering, admin panel, and real-time analytics',
      cta1: 'Get Started →',
      cta2: 'View Source',
      stat1: 'Platforms',
      stat2: 'Features',
      stat3: 'Tech Stack'
    },
    features: {
      badge: 'Features',
      title: 'Complete Solution for Food Delivery',
      desc: 'Full business loop from front-end ordering to back-end management',
      items: [
        { title: 'WeChat Mini Program', desc: 'Users can log in via WeChat, browse menus, add to cart, and pay online' },
        { title: 'Admin Dashboard', desc: 'Employee management, category management, dish management, order processing' },
        { title: 'Data Analytics', desc: 'ECharts visualization for revenue, user count, and order trends' },
        { title: 'Real-time Notifications', desc: 'WebSocket for instant order alerts and customer follow-ups' },
        { title: 'Secure Authentication', desc: 'JWT Token authorization with role-based access control' },
        { title: 'Cloud Storage', desc: 'Alibaba Cloud OSS for dish images and report files' }
      ]
    },
    tech: {
      badge: 'Tech Stack',
      title: 'Built with Modern Technologies',
      desc: 'Front-end and back-end separation architecture, scalable and reliable',
      items: [
        { name: 'Vue 3', desc: 'Frontend Framework' },
        { name: 'TypeScript', desc: 'Type Safety' },
        { name: 'Element UI', desc: 'UI Components' },
        { name: 'ECharts', desc: 'Data Visualization' },
        { name: 'Spring Boot', desc: 'Backend Framework' },
        { name: 'MyBatis', desc: 'ORM Framework' },
        { name: 'MySQL', desc: 'Relational Database' },
        { name: 'Redis', desc: 'Cache Database' },
        { name: 'WebSocket', desc: 'Real-time Communication' },
        { name: 'JWT', desc: 'Authentication' },
        { name: 'Nginx', desc: 'Reverse Proxy' },
        { name: 'UniApp', desc: 'Mini Program Framework' }
      ],
      arch: {
        user: 'User App',
        userDesc: 'WeChat Mini Program',
        admin: 'Admin Panel',
        adminDesc: 'Vue 3 + Element UI',
        server: 'Server',
        serverDesc: 'Spring Boot',
        data: 'Database',
        dataDesc: 'MySQL + Redis'
      }
    },
    screenshots: {
      badge: 'Screenshots',
      title: 'Clean & Efficient Interface',
      desc: 'Carefully designed admin dashboard for smooth operation',
      items: [
        { name: 'Dashboard' },
        { name: 'Data View' },
        { name: 'Orders' },
        { name: 'Set Meals' },
        { name: 'Dishes' },
        { name: 'Categories' },
        { name: 'Add Dish' },
        { name: 'Employees' },
        { name: 'Shop Status' }
      ]
    },
    quickstart: {
      badge: 'Quick Start',
      title: 'Start in Few Steps',
      desc: 'Get the entire system running quickly',
      steps: [
        { title: 'Clone Project', code: 'git clone https://github.com/Siborne/doupo-take-out.git' },
        { title: 'Configure Database', code: '# Run database initialization script\n# Configure application-dev.yml' },
        { title: 'Start Backend', code: '# Navigate to background/doupo-take-out\nmvn spring-boot:run' },
        { title: 'Start Frontend', code: '# Navigate to front/project-doupo-admin-vue-ts\nnpm install\nnpm run dev' }
      ],
      swagger: 'Visit Swagger docs to verify service status'
    },
    cta: {
      title: 'Start Using Doupo Express',
      desc: 'Open source, free, and customizable food delivery solution',
      github: 'Star on GitHub',
      docs: 'View Docs'
    },
    footer: {
      desc: 'Smart Food Delivery System\nAll-in-one Solution',
      features: 'Features',
      tech: 'Technology',
      resources: 'Resources',
      items: {
        features: ['WeChat Ordering', 'Admin Dashboard', 'Data Analytics'],
        tech: ['Vue 3 + TypeScript', 'Spring Boot', 'MySQL + Redis'],
        resources: ['GitHub', 'Quick Start', 'Screenshots']
      }
    }
  }
}

const i18n = createI18n({
  legacy: false,
  locale: 'zh',
  fallbackLocale: 'zh',
  messages
})

export default i18n
