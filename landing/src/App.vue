<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { useDarkMode } from './composables/useDarkMode'
import Icon from './components/Icon.vue'

const { t, locale } = useI18n()
const { isDark, toggle: toggleDark } = useDarkMode()
const mobileMenuOpen = ref(false)
const scrolled = ref(false)
const base = import.meta.env.BASE_URL

const toggleLocale = () => {
  locale.value = locale.value === 'zh' ? 'en' : 'zh'
  localStorage.setItem('locale', locale.value)
}

const handleScroll = () => {
  scrolled.value = window.scrollY > 20
}

const features = [
  { icon: 'device-phone' },
  { icon: 'computer-desktop' },
  { icon: 'chart-bar' },
  { icon: 'bolt' },
  { icon: 'shield-check' },
  { icon: 'cloud' }
]

const techIcons = [
  'cube',
  'code-bracket',
  'rectangle-group',
  'chart-pie',
  'leaf',
  'database',
  'table-cells',
  'bolt',
  'arrows-right-left',
  'key',
  'globe-alt',
  'device-phone'
]

const arch = [
  { icon: 'user', key: 'user' },
  { icon: 'computer-desktop', key: 'admin' },
  { icon: 'server', key: 'server' },
  { icon: 'database', key: 'data' }
]

const techGroups = [
  { key: 'frontend', items: [0, 1, 2, 3, 11] },
  { key: 'backend', items: [4, 5, 8, 9] },
  { key: 'data', items: [6, 7, 10] }
]

const screenshots = [
  { path: '1.workSpace.png' },
  { path: '2.dateView.png' },
  { path: '3.orderManger.png' },
  { path: '4.stemalManger.png' },
  { path: '5.dishManger.png' },
  { path: '6.manger.png' },
  { path: '7.addDishOrSteaml.png' },
  { path: '8.employeeManger.png' },
  { path: '9.shopStatusManger.png' }
]

const chartBars = [40, 65, 85, 100, 70, 45]

onMounted(() => {
  const savedLocale = localStorage.getItem('locale')
  if (savedLocale) {
    locale.value = savedLocale
  }

  window.addEventListener('scroll', handleScroll)
  handleScroll()

  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('visible')
        observer.unobserve(entry.target)
      }
    })
  }, { threshold: 0.05, rootMargin: '0px 0px -50px 0px' })

  document.querySelectorAll('.section-fade').forEach(el => observer.observe(el))
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<template>
  <div class="min-h-screen bg-slate-50 dark:bg-navy-950 transition-colors duration-300">
    <!-- Navigation -->
    <nav class="fixed top-0 left-0 right-0 z-50 transition-all duration-300"
         :class="scrolled
           ? 'bg-white/90 dark:bg-navy-950/90 backdrop-blur-lg shadow-sm border-b border-slate-200/60 dark:border-navy-800'
           : 'bg-transparent'">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <div class="flex items-center gap-2.5">
            <img :src="base + (isDark ? 'logo_dark.png' : 'logo_color.png')" alt="斗破快送" class="h-9" />
            <span class="text-lg font-bold" :class="isDark ? 'text-white' : 'text-navy-950'">斗破快送</span>
          </div>
          <div class="hidden md:flex items-center space-x-6">
            <a href="#features" class="transition-colors font-medium text-sm" :class="isDark ? 'text-slate-300 hover:text-primary-400' : 'text-slate-600 hover:text-primary-600'">{{ t('nav.features') }}</a>
            <a href="#tech" class="transition-colors font-medium text-sm" :class="isDark ? 'text-slate-300 hover:text-primary-400' : 'text-slate-600 hover:text-primary-600'">{{ t('nav.tech') }}</a>
            <a href="#screenshots" class="transition-colors font-medium text-sm" :class="isDark ? 'text-slate-300 hover:text-primary-400' : 'text-slate-600 hover:text-primary-600'">{{ t('nav.screenshots') }}</a>
            <a href="#quickstart" class="transition-colors font-medium text-sm" :class="isDark ? 'text-slate-300 hover:text-primary-400' : 'text-slate-600 hover:text-primary-600'">{{ t('nav.quickstart') }}</a>

            <button @click="toggleLocale" class="px-3 py-1.5 text-sm font-medium rounded-lg transition-all" :class="isDark ? 'text-slate-300 hover:text-primary-400 border border-navy-700' : 'text-slate-600 hover:text-primary-600 border border-slate-200'">
              {{ locale === 'zh' ? 'EN' : '中文' }}
            </button>

            <button @click="toggleDark" class="p-2 transition-colors" :class="isDark ? 'text-slate-300 hover:text-primary-400' : 'text-slate-600 hover:text-primary-600'">
              <svg v-if="!isDark" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
              </svg>
              <svg v-else class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
              </svg>
            </button>

            <a href="https://github.com/Siborne/doupo-take-out" target="_blank"
              class="inline-flex items-center gap-2 px-4 py-2 rounded-full font-medium text-sm transition-colors"
              :class="isDark
                ? 'bg-white text-navy-950 hover:bg-slate-100'
                : 'bg-navy-950 text-white hover:bg-navy-900'">
              <Icon name="github" class="w-4 h-4" />
              {{ t('nav.github') }}
            </a>
          </div>
          <button @click="mobileMenuOpen = !mobileMenuOpen" class="md:hidden p-2" :class="isDark ? 'text-slate-300' : 'text-slate-700'">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path v-if="!mobileMenuOpen" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
              <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
      </div>
      <div v-if="mobileMenuOpen" class="md:hidden bg-white dark:bg-navy-950 border-t border-slate-100 dark:border-navy-800">
        <div class="px-4 py-4 space-y-3">
          <a href="#features" class="block text-slate-600 dark:text-slate-300 hover:text-primary-500 font-medium">{{ t('nav.features') }}</a>
          <a href="#tech" class="block text-slate-600 dark:text-slate-300 hover:text-primary-500 font-medium">{{ t('nav.tech') }}</a>
          <a href="#screenshots" class="block text-slate-600 dark:text-slate-300 hover:text-primary-500 font-medium">{{ t('nav.screenshots') }}</a>
          <a href="#quickstart" class="block text-slate-600 dark:text-slate-300 hover:text-primary-500 font-medium">{{ t('nav.quickstart') }}</a>
          <div class="flex items-center gap-4 pt-2">
            <button @click="toggleLocale" class="px-3 py-1.5 text-sm font-medium text-slate-600 dark:text-slate-300 border border-slate-200 dark:border-navy-700 rounded-lg">
              {{ locale === 'zh' ? 'EN' : '中文' }}
            </button>
            <button @click="toggleDark" class="p-2 text-slate-600 dark:text-slate-300">
              <svg v-if="!isDark" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
              </svg>
              <svg v-else class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
              </svg>
            </button>
          </div>
          <a href="https://github.com/Siborne/doupo-take-out" target="_blank"
            class="flex items-center justify-center gap-2 px-4 py-2 bg-navy-950 dark:bg-white text-white dark:text-navy-950 rounded-full font-medium text-sm">
            <Icon name="github" class="w-4 h-4" />
            {{ t('nav.github') }}
          </a>
        </div>
      </div>
    </nav>

    <!-- Hero Section -->
    <section class="relative overflow-hidden pt-28 pb-20 md:pt-36 md:pb-28">
      <div class="absolute inset-0 hero-gradient opacity-[0.55] dark:opacity-0"></div>
      <div class="absolute -top-28 -right-28 w-[30rem] h-[30rem] bg-primary-300/40 dark:bg-primary-500/10 rounded-full blur-3xl"></div>
      <div class="absolute -bottom-32 -left-24 w-[28rem] h-[28rem] bg-violet-300/40 dark:bg-violet-500/10 rounded-full blur-3xl"></div>

      <div class="relative z-10 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 grid lg:grid-cols-2 gap-14 items-center">
        <div class="text-center lg:text-left">
          <div class="inline-flex items-center gap-2 px-4 py-2 rounded-full bg-white/70 dark:bg-primary-900/40 backdrop-blur border border-primary-200/60 dark:border-primary-800 text-primary-700 dark:text-primary-300 text-sm font-semibold mb-6 shadow-sm">
            <span class="w-2 h-2 bg-primary-500 rounded-full animate-pulse"></span>
            {{ t('hero.badge') }}
          </div>
          <h1 class="text-4xl sm:text-5xl md:text-6xl font-extrabold text-navy-950 dark:text-white leading-tight mb-6">
            {{ t('hero.title1') }} <span class="gradient-text">{{ t('hero.title2') }}</span>
          </h1>
          <p class="text-lg md:text-xl text-slate-500 dark:text-slate-400 mb-10 max-w-xl mx-auto lg:mx-0 whitespace-pre-line">
            {{ t('hero.desc') }}
          </p>
          <div class="flex flex-col sm:flex-row gap-4 justify-center lg:justify-start mb-12">
            <a href="#quickstart"
              class="inline-flex items-center justify-center gap-2 px-7 py-3.5 gradient-bg text-white rounded-full font-bold text-base shadow-lg shadow-primary-500/25 hover:shadow-xl hover:shadow-primary-500/35 hover:-translate-y-0.5 transition-all duration-300">
              {{ t('hero.cta1') }}
              <Icon name="arrow-right" class="w-4 h-4" />
            </a>
            <a href="https://github.com/Siborne/doupo-take-out" target="_blank"
              class="inline-flex items-center justify-center gap-2 px-7 py-3.5 bg-white dark:bg-transparent text-navy-950 dark:text-white rounded-full font-bold text-base border-2 border-navy-950 dark:border-navy-700 hover:bg-navy-950 hover:text-white dark:hover:bg-white dark:hover:text-navy-950 transition-all duration-300">
              <Icon name="github" class="w-4 h-4" />
              {{ t('hero.cta2') }}
            </a>
          </div>
          <div class="grid grid-cols-3 gap-6 max-w-sm mx-auto lg:mx-0">
            <div>
              <div class="text-4xl font-extrabold gradient-text">3</div>
              <div class="text-sm text-slate-500 dark:text-slate-400 font-medium">{{ t('hero.stat1') }}</div>
            </div>
            <div>
              <div class="text-4xl font-extrabold gradient-text">20+</div>
              <div class="text-sm text-slate-500 dark:text-slate-400 font-medium">{{ t('hero.stat2') }}</div>
            </div>
            <div>
              <div class="text-4xl font-extrabold gradient-text">12</div>
              <div class="text-sm text-slate-500 dark:text-slate-400 font-medium">{{ t('hero.stat3') }}</div>
            </div>
          </div>
        </div>

        <div class="relative mx-auto w-full max-w-xl">
          <div class="absolute -inset-8 gradient-bg opacity-20 dark:opacity-15 blur-3xl rounded-full"></div>
          <div class="relative rounded-2xl border border-slate-200 dark:border-navy-800 shadow-2xl shadow-primary-900/10 bg-white dark:bg-navy-900 overflow-hidden">
            <div class="flex items-center gap-1.5 px-4 py-3 border-b border-slate-100 dark:border-navy-800 bg-slate-50 dark:bg-navy-950">
              <span class="w-3 h-3 rounded-full bg-rose-400"></span>
              <span class="w-3 h-3 rounded-full bg-amber-400"></span>
              <span class="w-3 h-3 rounded-full bg-emerald-400"></span>
              <div class="ml-3 flex-1 h-6 rounded-md bg-white dark:bg-navy-900 border border-slate-200 dark:border-navy-800 flex items-center px-3 text-xs text-slate-400">doupo-take-out</div>
            </div>
            <div class="flex">
              <div class="hidden sm:flex w-14 flex-col items-center gap-3 py-4 border-r border-slate-100 dark:border-navy-800">
                <span class="w-8 h-8 rounded-lg gradient-bg"></span>
                <span class="w-6 h-6 rounded-md bg-primary-100 dark:bg-primary-900/40"></span>
                <span class="w-6 h-6 rounded-md bg-slate-100 dark:bg-navy-800"></span>
                <span class="w-6 h-6 rounded-md bg-slate-100 dark:bg-navy-800"></span>
                <span class="w-6 h-6 rounded-md bg-slate-100 dark:bg-navy-800"></span>
              </div>
              <div class="flex-1 p-4 sm:p-5 space-y-4">
                <div class="grid grid-cols-3 gap-3">
                  <div class="rounded-xl border border-slate-100 dark:border-navy-800 p-3">
                    <div class="h-2 w-12 rounded-full bg-slate-200 dark:bg-navy-800 mb-2"></div>
                    <div class="text-lg font-bold text-slate-800 dark:text-white">1,072</div>
                  </div>
                  <div class="rounded-xl border border-slate-100 dark:border-navy-800 p-3">
                    <div class="h-2 w-12 rounded-full bg-slate-200 dark:bg-navy-800 mb-2"></div>
                    <div class="text-lg font-bold text-slate-800 dark:text-white">24</div>
                  </div>
                  <div class="rounded-xl border border-slate-100 dark:border-navy-800 p-3">
                    <div class="h-2 w-12 rounded-full bg-slate-200 dark:bg-navy-800 mb-2"></div>
                    <div class="text-lg font-bold text-slate-800 dark:text-white">98%</div>
                  </div>
                </div>
                <div class="rounded-xl border border-slate-100 dark:border-navy-800 p-4">
                  <div class="flex items-end gap-2 h-24">
                    <span v-for="(bar, index) in chartBars" :key="index"
                      class="flex-1 rounded-t-md transition-colors"
                      :class="index === 3 ? 'gradient-bg' : index % 2 === 0 ? 'bg-primary-500/50' : 'bg-primary-500/25'"
                      :style="{ height: bar + '%' }"></span>
                  </div>
                </div>
                <div class="space-y-2">
                  <div v-for="row in 3" :key="row" class="flex items-center gap-3 rounded-xl border border-slate-100 dark:border-navy-800 p-3">
                    <span class="w-8 h-8 rounded-full shrink-0" :class="row === 1 ? 'bg-primary-100 dark:bg-primary-900/40' : row === 2 ? 'bg-violet-100 dark:bg-violet-900/40' : 'bg-sky-100 dark:bg-sky-900/40'"></span>
                    <div class="flex-1 space-y-1.5">
                      <div class="h-2 w-24 rounded-full bg-slate-200 dark:bg-navy-800"></div>
                      <div class="h-2 w-16 rounded-full bg-slate-100 dark:bg-navy-800/70"></div>
                    </div>
                    <span class="w-2 h-2 rounded-full shrink-0" :class="row === 1 ? 'bg-emerald-500' : row === 2 ? 'bg-amber-500' : 'bg-sky-500'"></span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="glass-card absolute -left-4 -top-5 px-4 py-3 flex items-center gap-3 rounded-2xl shadow-xl">
            <span class="w-10 h-10 rounded-xl bg-primary-100 dark:bg-primary-900/50 text-primary-600 dark:text-primary-400 flex items-center justify-center">
              <Icon name="bolt" class="w-5 h-5" />
            </span>
            <div>
              <div class="text-sm font-bold text-navy-950 dark:text-white">WebSocket</div>
              <div class="text-xs text-slate-500 dark:text-slate-400">{{ t('features.items[3].title') }}</div>
            </div>
          </div>
          <div class="glass-card absolute -right-4 bottom-10 px-4 py-3 flex items-center gap-3 rounded-2xl shadow-xl">
            <span class="w-10 h-10 rounded-xl bg-violet-100 dark:bg-violet-900/50 text-violet-600 dark:text-violet-400 flex items-center justify-center">
              <Icon name="shield-check" class="w-5 h-5" />
            </span>
            <div>
              <div class="text-sm font-bold text-navy-950 dark:text-white">JWT</div>
              <div class="text-xs text-slate-500 dark:text-slate-400">{{ t('features.items[4].title') }}</div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Features Section -->
    <section id="features" class="py-24 md:py-28 bg-white dark:bg-navy-950 transition-colors duration-300">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16 section-fade">
          <div class="inline-block px-4 py-2 bg-primary-50 dark:bg-primary-900/40 rounded-full text-sm text-primary-600 dark:text-primary-300 font-semibold mb-4 border border-primary-100 dark:border-primary-800">{{ t('features.badge') }}</div>
          <h2 class="text-4xl md:text-5xl font-extrabold text-navy-950 dark:text-white mb-4">{{ t('features.title') }}</h2>
          <p class="text-xl text-slate-500 dark:text-slate-400 max-w-2xl mx-auto font-light">
            {{ t('features.desc') }}
          </p>
        </div>
        <div class="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="(feature, index) in features" :key="index"
            class="feature-card bg-white dark:bg-navy-900 p-8 rounded-3xl shadow-sm border border-slate-100 dark:border-navy-800 section-fade"
            :class="'delay-' + (index % 6 + 1)">
            <div class="w-14 h-14 bg-primary-50 dark:bg-primary-900/40 rounded-2xl flex items-center justify-center mb-5 text-primary-600 dark:text-primary-400 border border-primary-100 dark:border-primary-800">
              <Icon :name="feature.icon" class="w-7 h-7" />
            </div>
            <h3 class="text-xl font-bold text-navy-950 dark:text-white mb-3">{{ t(`features.items[${index}].title`) }}</h3>
            <p class="text-slate-500 dark:text-slate-400 leading-relaxed">{{ t(`features.items[${index}].desc`) }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Tech Stack Section -->
    <section id="tech" class="py-24 md:py-28 bg-gradient-to-b from-slate-50 to-white dark:from-navy-950 dark:to-navy-900 transition-colors duration-300">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16 section-fade">
          <div class="inline-block px-4 py-2 bg-primary-50 dark:bg-primary-900/40 rounded-full text-sm text-primary-600 dark:text-primary-300 font-semibold mb-4 border border-primary-100 dark:border-primary-800">{{ t('tech.badge') }}</div>
          <h2 class="text-4xl md:text-5xl font-extrabold text-navy-950 dark:text-white mb-4">{{ t('tech.title') }}</h2>
          <p class="text-xl text-slate-500 dark:text-slate-400 max-w-2xl mx-auto font-light">
            {{ t('tech.desc') }}
          </p>
        </div>
        <!-- Architecture Flow -->
        <div class="mb-16 section-fade">
          <div class="hidden lg:flex items-stretch">
            <template v-for="(item, index) in arch" :key="item.key">
              <div class="flex-1 rounded-2xl border border-slate-200 dark:border-navy-800 bg-white dark:bg-navy-900 p-5 text-center shadow-sm">
                <div class="w-12 h-12 mx-auto mb-3 rounded-xl bg-primary-50 dark:bg-primary-900/40 text-primary-600 dark:text-primary-400 flex items-center justify-center border border-primary-100 dark:border-primary-800">
                  <Icon :name="item.icon" class="w-6 h-6" />
                </div>
                <div class="font-bold text-navy-950 dark:text-white">{{ t(`tech.arch.${item.key}`) }}</div>
                <div class="text-sm text-slate-500 dark:text-slate-400 mt-1">{{ t(`tech.arch.${item.key}Desc`) }}</div>
              </div>
              <div v-if="index < arch.length - 1" class="flex items-center px-3 text-primary-400">
                <Icon name="arrow-right" class="w-5 h-5" />
              </div>
            </template>
          </div>
          <div class="grid sm:grid-cols-2 gap-4 lg:hidden">
            <div v-for="item in arch" :key="item.key" class="rounded-2xl border border-slate-200 dark:border-navy-800 bg-white dark:bg-navy-900 p-5 flex items-center gap-4 shadow-sm">
              <span class="w-11 h-11 rounded-xl bg-primary-50 dark:bg-primary-900/40 text-primary-600 dark:text-primary-400 flex items-center justify-center shrink-0 border border-primary-100 dark:border-primary-800">
                <Icon :name="item.icon" class="w-6 h-6" />
              </span>
              <div>
                <div class="font-bold text-navy-950 dark:text-white">{{ t(`tech.arch.${item.key}`) }}</div>
                <div class="text-sm text-slate-500 dark:text-slate-400">{{ t(`tech.arch.${item.key}Desc`) }}</div>
              </div>
            </div>
          </div>
        </div>

        <!-- Grouped Tech Stack -->
        <div class="space-y-10">
          <div v-for="group in techGroups" :key="group.key" class="section-fade">
            <div class="flex items-center gap-3 mb-5">
              <span class="text-sm font-bold tracking-widest uppercase text-primary-600 dark:text-primary-400">{{ t(`tech.groups.${group.key}`) }}</span>
              <div class="h-px flex-1 bg-slate-200 dark:bg-navy-800"></div>
              <span class="text-xs font-medium text-slate-400 dark:text-slate-500">{{ group.items.length }}</span>
            </div>
            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
              <div v-for="index in group.items" :key="index"
                class="tech-icon bg-white dark:bg-navy-900 rounded-2xl p-4 flex items-center gap-3 border border-slate-100 dark:border-navy-800 shadow-sm">
                <span class="w-10 h-10 rounded-xl bg-primary-50 dark:bg-primary-900/40 text-primary-600 dark:text-primary-400 flex items-center justify-center shrink-0">
                  <Icon :name="techIcons[index]" class="w-5 h-5" />
                </span>
                <div class="min-w-0">
                  <div class="font-bold text-sm text-navy-950 dark:text-white">{{ t(`tech.items[${index}].name`) }}</div>
                  <div class="text-xs text-slate-400 dark:text-slate-500 truncate">{{ t(`tech.items[${index}].desc`) }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Screenshots Section -->
    <section id="screenshots" class="py-24 md:py-28 bg-white dark:bg-navy-950 transition-colors duration-300">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16 section-fade">
          <div class="inline-block px-4 py-2 bg-primary-50 dark:bg-primary-900/40 rounded-full text-sm text-primary-600 dark:text-primary-300 font-semibold mb-4 border border-primary-100 dark:border-primary-800">{{ t('screenshots.badge') }}</div>
          <h2 class="text-4xl md:text-5xl font-extrabold text-navy-950 dark:text-white mb-4">{{ t('screenshots.title') }}</h2>
          <p class="text-xl text-slate-500 dark:text-slate-400 max-w-2xl mx-auto font-light">
            {{ t('screenshots.desc') }}
          </p>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="(screenshot, index) in screenshots" :key="index"
            class="screenshot-item bg-white dark:bg-navy-900 rounded-2xl overflow-hidden shadow-sm border border-slate-100 dark:border-navy-800 hover:shadow-xl transition-all duration-300 section-fade"
            :class="'delay-' + (index % 6 + 1)">
            <div class="aspect-video bg-slate-50 dark:bg-navy-800 flex items-center justify-center overflow-hidden">
              <img :src="base + 'images/' + screenshot.path" :alt="t(`screenshots.items[${index}].name`)"
                class="w-full h-full object-cover" loading="lazy" />
            </div>
            <div class="p-4 text-center font-semibold text-navy-950 dark:text-white">{{ t(`screenshots.items[${index}].name`) }}</div>
          </div>
        </div>
      </div>
    </section>

    <!-- Quick Start Section -->
    <section id="quickstart" class="py-24 md:py-28 bg-slate-50 dark:bg-navy-900 transition-colors duration-300">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16 section-fade">
          <div class="inline-block px-4 py-2 bg-primary-50 dark:bg-primary-900/40 rounded-full text-sm text-primary-600 dark:text-primary-300 font-semibold mb-4 border border-primary-100 dark:border-primary-800">{{ t('quickstart.badge') }}</div>
          <h2 class="text-4xl md:text-5xl font-extrabold text-navy-950 dark:text-white mb-4">{{ t('quickstart.title') }}</h2>
          <p class="text-xl text-slate-500 dark:text-slate-400 max-w-2xl mx-auto font-light">
            {{ t('quickstart.desc') }}
          </p>
        </div>
        <div class="max-w-4xl mx-auto">
          <div class="overflow-hidden rounded-2xl border border-navy-800 bg-navy-950 shadow-2xl shadow-navy-950/30">
            <div class="flex items-center gap-1.5 px-4 py-3 border-b border-navy-800 bg-navy-900/80">
              <span class="w-3 h-3 rounded-full bg-rose-400"></span>
              <span class="w-3 h-3 rounded-full bg-amber-400"></span>
              <span class="w-3 h-3 rounded-full bg-emerald-400"></span>
              <span class="ml-3 text-sm font-medium text-slate-400">{{ t('quickstart.terminal') }}</span>
            </div>
            <div class="divide-y divide-navy-800">
              <div v-for="step in 4" :key="step" class="flex gap-4 p-5 sm:p-6 section-fade" :class="'delay-' + step">
                <div class="flex-shrink-0 w-8 h-8 gradient-bg rounded-lg flex items-center justify-center text-white font-bold text-sm shadow-lg shadow-primary-500/25">{{ step }}</div>
                <div class="flex-1 min-w-0">
                  <div class="flex items-center gap-2 mb-2.5">
                    <span class="font-mono text-xs text-primary-400">$</span>
                    <h3 class="text-white font-semibold">{{ t(`quickstart.steps[${step - 1}].title`) }}</h3>
                  </div>
                  <pre class="font-mono text-sm text-slate-300 whitespace-pre-line overflow-x-auto">{{ t(`quickstart.steps[${step - 1}].code`) }}</pre>
                </div>
              </div>
            </div>
          </div>
          <div class="mt-10 text-center section-fade delay-5">
            <p class="text-slate-500 dark:text-slate-400 mb-4">{{ t('quickstart.swagger') }}</p>
            <div class="inline-flex items-center gap-2 bg-white dark:bg-navy-800 px-6 py-3 rounded-full font-mono text-navy-950 dark:text-white border border-slate-200 dark:border-navy-700 shadow-sm">
              <Icon name="globe-alt" class="w-4 h-4 text-primary-500" />
              http://localhost:8080/docs.html
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="py-24 md:py-28 bg-navy-950 dark:bg-navy-900 relative overflow-hidden transition-colors duration-300">
      <div class="absolute inset-0 opacity-25">
        <div class="absolute top-0 left-0 w-96 h-96 bg-primary-500 rounded-full filter blur-3xl -translate-x-1/2 -translate-y-1/2"></div>
        <div class="absolute bottom-0 right-0 w-96 h-96 bg-violet-500 rounded-full filter blur-3xl translate-x-1/2 translate-y-1/2"></div>
      </div>
      <div class="max-w-4xl mx-auto px-4 text-center relative z-10 section-fade">
        <h2 class="text-4xl md:text-5xl font-extrabold text-white mb-6">{{ t('cta.title') }}</h2>
        <p class="text-xl mb-10 text-slate-300 font-light">
          {{ t('cta.desc') }}
        </p>
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
          <a href="https://github.com/Siborne/doupo-take-out" target="_blank"
            class="inline-flex items-center justify-center gap-2 px-8 py-4 gradient-bg text-white rounded-full font-bold text-lg shadow-lg shadow-primary-500/30 hover:shadow-xl hover:shadow-primary-500/40 transition-all duration-300">
            <Icon name="github" class="w-5 h-5" />
            {{ t('cta.github') }}
          </a>
          <a href="#quickstart"
            class="px-8 py-4 border-2 border-white text-white rounded-full font-bold text-lg hover:bg-white hover:text-navy-950 transition-all duration-300">
            {{ t('cta.docs') }}
          </a>
        </div>
      </div>
    </section>

    <!-- Footer -->
    <footer class="bg-navy-950 text-slate-400 py-12 border-t border-navy-800 transition-colors duration-300">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="grid md:grid-cols-4 gap-8">
          <div>
            <div class="flex items-center mb-4">
              <img :src="base + 'logo_write.png'" alt="斗破快送" class="h-10" />
            </div>
            <p class="text-sm whitespace-pre-line">{{ t('footer.desc') }}</p>
          </div>
          <div>
            <h4 class="text-white font-semibold mb-4">{{ t('footer.features') }}</h4>
            <ul class="space-y-2.5 text-sm">
              <li v-for="(item, index) in 3" :key="index">
                <a href="#features" class="hover:text-primary-400 transition-colors">{{ t(`footer.items.features[${index}]`) }}</a>
              </li>
            </ul>
          </div>
          <div>
            <h4 class="text-white font-semibold mb-4">{{ t('footer.tech') }}</h4>
            <ul class="space-y-2.5 text-sm">
              <li v-for="(item, index) in 3" :key="index">
                <a href="#tech" class="hover:text-primary-400 transition-colors">{{ t(`footer.items.tech[${index}]`) }}</a>
              </li>
            </ul>
          </div>
          <div>
            <h4 class="text-white font-semibold mb-4">{{ t('footer.resources') }}</h4>
            <ul class="space-y-2.5 text-sm">
              <li v-for="(item, index) in 3" :key="index">
                <a :href="index === 0 ? 'https://github.com/Siborne/doupo-take-out' : index === 1 ? '#quickstart' : '#screenshots'"
                   :target="index === 0 ? '_blank' : undefined"
                   class="inline-flex items-center gap-1.5 hover:text-primary-400 transition-colors">
                  <Icon v-if="index === 0" name="github" class="w-4 h-4" />
                  {{ t(`footer.items.resources[${index}]`) }}
                </a>
              </li>
            </ul>
          </div>
        </div>
        <div class="border-t border-navy-800 mt-8 pt-8 text-center text-sm">
          <p>© 2024 斗破快送 · Built with
            <svg class="inline-block w-4 h-4 text-primary-400 align-text-bottom" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
              <path d="M11.645 20.91l-.007-.003-.022-.012a15.247 15.247 0 01-.383-.218 25.18 25.18 0 01-4.244-3.17C4.688 15.36 2.25 12.174 2.25 8.25 2.25 5.322 4.714 3 7.688 3A5.5 5.5 0 0112 5.052 5.5 5.5 0 0116.313 3c2.973 0 5.437 2.322 5.437 5.25 0 3.925-2.438 7.111-4.739 9.256a25.175 25.175 0 01-4.244 3.17 15.247 15.247 0 01-.383.219l-.022.012-.007.004-.003.001a.752.752 0 01-.704 0l-.003-.001z" />
            </svg>
          </p>
        </div>
      </div>
    </footer>
  </div>
</template>
