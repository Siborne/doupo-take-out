<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { useDarkMode } from './composables/useDarkMode'

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
  { icon: '📱' },
  { icon: '🖥️' },
  { icon: '📊' },
  { icon: '⚡' },
  { icon: '🔐' },
  { icon: '☁️' }
]

const techIcons = ['💚', '💙', '🎨', '📈', '🍃', '🗄️', '🐬', '🔴', '🔌', '🔑', '🌐', '📱']

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
  <div class="min-h-screen bg-white dark:bg-navy-950 transition-colors duration-300">
    <!-- Navigation -->
    <nav class="fixed top-0 left-0 right-0 z-50 transition-all duration-300"
         :class="scrolled 
           ? 'bg-white/95 dark:bg-navy-950/95 backdrop-blur-lg shadow-sm border-b border-gray-100 dark:border-gray-800' 
           : 'bg-transparent'">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <div class="flex items-center gap-2.5">
            <img :src="base + (isDark ? 'logo_dark.png' : 'logo_color.png')" alt="斗破快送" class="h-9" />
            <span class="text-lg font-bold" :class="isDark ? 'text-white' : 'text-navy-950'">斗破快送</span>
          </div>
          <div class="hidden md:flex items-center space-x-6">
            <a href="#features" class="transition-colors font-medium text-sm" :class="isDark ? 'text-gray-300 hover:text-orange-400' : 'text-gray-700 hover:text-orange-500'">{{ t('nav.features') }}</a>
            <a href="#tech" class="transition-colors font-medium text-sm" :class="isDark ? 'text-gray-300 hover:text-orange-400' : 'text-gray-700 hover:text-orange-500'">{{ t('nav.tech') }}</a>
            <a href="#screenshots" class="transition-colors font-medium text-sm" :class="isDark ? 'text-gray-300 hover:text-orange-400' : 'text-gray-700 hover:text-orange-500'">{{ t('nav.screenshots') }}</a>
            <a href="#quickstart" class="transition-colors font-medium text-sm" :class="isDark ? 'text-gray-300 hover:text-orange-400' : 'text-gray-700 hover:text-orange-500'">{{ t('nav.quickstart') }}</a>
            
            <!-- Language Toggle -->
            <button @click="toggleLocale" class="px-3 py-1.5 text-sm font-medium rounded-lg transition-all" :class="isDark ? 'text-gray-300 hover:text-orange-400 border border-gray-700' : 'text-gray-700 hover:text-orange-500 border border-gray-200'">
              {{ locale === 'zh' ? 'EN' : '中文' }}
            </button>
            
            <!-- Dark Mode Toggle -->
            <button @click="toggleDark" class="p-2 transition-colors" :class="isDark ? 'text-gray-300 hover:text-orange-400' : 'text-gray-700 hover:text-orange-500'">
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
                ? 'bg-white text-navy-950 hover:bg-gray-100' 
                : 'bg-navy-950 text-white hover:bg-navy-900'">
              <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 16 16"><path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"/></svg>
              {{ t('nav.github') }}
            </a>
          </div>
          <button @click="mobileMenuOpen = !mobileMenuOpen" class="md:hidden p-2" :class="isDark ? 'text-gray-300' : 'text-gray-700'">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path v-if="!mobileMenuOpen" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
              <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
      </div>
      <div v-if="mobileMenuOpen" class="md:hidden bg-white dark:bg-navy-950 border-t border-gray-100 dark:border-gray-800">
        <div class="px-4 py-4 space-y-3">
          <a href="#features" class="block text-gray-600 dark:text-gray-300 hover:text-orange-500 font-medium">{{ t('nav.features') }}</a>
          <a href="#tech" class="block text-gray-600 dark:text-gray-300 hover:text-orange-500 font-medium">{{ t('nav.tech') }}</a>
          <a href="#screenshots" class="block text-gray-600 dark:text-gray-300 hover:text-orange-500 font-medium">{{ t('nav.screenshots') }}</a>
          <a href="#quickstart" class="block text-gray-600 dark:text-gray-300 hover:text-orange-500 font-medium">{{ t('nav.quickstart') }}</a>
          <div class="flex items-center gap-4 pt-2">
            <button @click="toggleLocale" class="px-3 py-1.5 text-sm font-medium text-gray-600 dark:text-gray-300 border border-gray-200 dark:border-gray-700 rounded-lg">
              {{ locale === 'zh' ? 'EN' : '中文' }}
            </button>
            <button @click="toggleDark" class="p-2 text-gray-600 dark:text-gray-300">
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
            <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 16 16"><path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"/></svg>
            {{ t('nav.github') }}
          </a>
        </div>
      </div>
    </nav>

    <!-- Hero Section -->
    <section class="relative min-h-screen flex items-center justify-center overflow-hidden pt-16">
      <!-- Light mode background -->
      <div class="absolute inset-0 hero-gradient opacity-10 dark:hidden"></div>
      <div class="absolute top-20 left-10 w-80 h-80 bg-orange-400 rounded-full mix-blend-multiply filter blur-3xl opacity-30 animate-float dark:hidden"></div>
      <div class="absolute top-40 right-10 w-80 h-80 bg-orange-200 rounded-full mix-blend-multiply filter blur-3xl opacity-30 animate-float-delay dark:hidden"></div>
      <div class="absolute bottom-20 left-1/3 w-80 h-80 bg-orange-300 rounded-full mix-blend-multiply filter blur-3xl opacity-30 animate-float dark:hidden"></div>
      <!-- Dark mode background -->
      <div class="absolute inset-0 bg-navy-950 dark:block hidden"></div>
      <div class="absolute top-20 left-10 w-96 h-96 bg-orange-500/10 rounded-full filter blur-3xl animate-float hidden dark:block"></div>
      <div class="absolute top-40 right-10 w-96 h-96 bg-orange-400/10 rounded-full filter blur-3xl animate-float-delay hidden dark:block"></div>
      <div class="absolute bottom-20 left-1/3 w-96 h-96 bg-orange-500/5 rounded-full filter blur-3xl animate-float hidden dark:block"></div>

      <div class="relative z-10 text-center px-4 max-w-5xl mx-auto">
        <div class="inline-flex items-center gap-2 px-5 py-2.5 bg-orange-100 dark:bg-orange-900/30 rounded-full text-sm text-orange-600 dark:text-orange-400 font-semibold mb-8 animate-fade-in">
          <span class="w-2 h-2 bg-orange-500 rounded-full animate-pulse"></span>
          {{ t('hero.badge') }}
        </div>
        <h1 class="text-5xl md:text-7xl font-extrabold text-navy-950 dark:text-white mb-6 animate-slide-up leading-tight">
          {{ t('hero.title1') }}
          <span class="gradient-text">{{ t('hero.title2') }}</span>
        </h1>
        <p class="text-xl md:text-2xl text-gray-500 dark:text-gray-400 mb-10 max-w-3xl mx-auto animate-slide-up font-light" style="animation-delay: 0.2s">
          {{ t('hero.desc') }}
        </p>
        <div class="flex flex-col sm:flex-row gap-4 justify-center animate-slide-up" style="animation-delay: 0.4s">
          <a href="#quickstart"
            class="px-8 py-4 gradient-bg text-white rounded-full font-bold text-lg hover:shadow-lg hover:shadow-orange-300/50 transition-all duration-300 hover:-translate-y-1">
            {{ t('hero.cta1') }}
          </a>
          <a href="https://github.com/Siborne/doupo-take-out" target="_blank"
            class="inline-flex items-center justify-center gap-2 px-8 py-4 bg-white dark:bg-transparent text-navy-950 dark:text-white rounded-full font-bold text-lg border-2 border-navy-950 dark:border-white hover:bg-navy-950 hover:text-white dark:hover:bg-white dark:hover:text-navy-950 transition-all duration-300">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 16 16"><path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"/></svg>
            {{ t('hero.cta2') }}
          </a>
        </div>
        <div class="mt-16 grid grid-cols-3 gap-8 max-w-lg mx-auto animate-fade-in" style="animation-delay: 0.6s">
          <div>
            <div class="text-4xl font-extrabold gradient-text">3</div>
            <div class="text-sm text-gray-500 dark:text-gray-400 font-medium">{{ t('hero.stat1') }}</div>
          </div>
          <div>
            <div class="text-4xl font-extrabold gradient-text">20+</div>
            <div class="text-sm text-gray-500 dark:text-gray-400 font-medium">{{ t('hero.stat2') }}</div>
          </div>
          <div>
            <div class="text-4xl font-extrabold gradient-text">12</div>
            <div class="text-sm text-gray-500 dark:text-gray-400 font-medium">{{ t('hero.stat3') }}</div>
          </div>
        </div>
      </div>

      <div class="absolute bottom-10 left-1/2 -translate-x-1/2 animate-bounce">
        <svg class="w-8 h-8 text-orange-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 14l-7 7m0 0l-7-7m7 7V3" />
        </svg>
      </div>
    </section>

    <!-- Features Section -->
    <section id="features" class="py-28 bg-white dark:bg-navy-950 transition-colors duration-300">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16 section-fade">
          <div class="inline-block px-4 py-2 bg-orange-100 dark:bg-orange-900/30 rounded-full text-sm text-orange-600 dark:text-orange-400 font-semibold mb-4">{{ t('features.badge') }}</div>
          <h2 class="text-4xl md:text-5xl font-extrabold text-navy-950 dark:text-white mb-4">{{ t('features.title') }}</h2>
          <p class="text-xl text-gray-500 dark:text-gray-400 max-w-2xl mx-auto font-light">
            {{ t('features.desc') }}
          </p>
        </div>
        <div class="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
          <div v-for="(feature, index) in features" :key="index"
            class="feature-card bg-white dark:bg-navy-900 p-8 rounded-3xl shadow-sm border border-gray-100 dark:border-gray-800 section-fade"
            :class="'delay-' + (index % 6 + 1)">
            <div class="w-14 h-14 bg-orange-100 dark:bg-orange-900/30 rounded-2xl flex items-center justify-center text-2xl mb-5">{{ feature.icon }}</div>
            <h3 class="text-xl font-bold text-navy-950 dark:text-white mb-3">{{ t(`features.items[${index}].title`) }}</h3>
            <p class="text-gray-500 dark:text-gray-400 leading-relaxed">{{ t(`features.items[${index}].desc`) }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Tech Stack Section -->
    <section id="tech" class="py-28 bg-gradient-to-b from-white to-orange-50 dark:from-navy-950 dark:to-navy-900 transition-colors duration-300">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16 section-fade">
          <div class="inline-block px-4 py-2 bg-orange-100 dark:bg-orange-900/30 rounded-full text-sm text-orange-600 dark:text-orange-400 font-semibold mb-4">{{ t('tech.badge') }}</div>
          <h2 class="text-4xl md:text-5xl font-extrabold text-navy-950 dark:text-white mb-4">{{ t('tech.title') }}</h2>
          <p class="text-xl text-gray-500 dark:text-gray-400 max-w-2xl mx-auto font-light">
            {{ t('tech.desc') }}
          </p>
        </div>
        <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-5">
          <div v-for="(icon, index) in techIcons" :key="index"
            class="tech-icon bg-white dark:bg-navy-900 p-6 rounded-2xl text-center cursor-default border border-gray-100 dark:border-gray-800 shadow-sm section-fade"
            :class="'delay-' + (index % 6 + 1)">
            <div class="text-4xl mb-3">{{ icon }}</div>
            <div class="font-bold text-navy-950 dark:text-white mb-1">{{ t(`tech.items[${index}].name`) }}</div>
            <div class="text-sm text-gray-400 dark:text-gray-500">{{ t(`tech.items[${index}].desc`) }}</div>
          </div>
        </div>
        <div class="mt-16 p-8 bg-navy-950 dark:bg-navy-900 rounded-3xl section-fade">
          <div class="grid md:grid-cols-4 gap-8 text-center">
            <div>
              <div class="text-3xl mb-2">👤</div>
              <div class="font-bold text-white">{{ t('tech.arch.user') }}</div>
              <div class="text-sm text-orange-300">{{ t('tech.arch.userDesc') }}</div>
            </div>
            <div>
              <div class="text-3xl mb-2">🖥️</div>
              <div class="font-bold text-white">{{ t('tech.arch.admin') }}</div>
              <div class="text-sm text-orange-300">{{ t('tech.arch.adminDesc') }}</div>
            </div>
            <div>
              <div class="text-3xl mb-2">⚙️</div>
              <div class="font-bold text-white">{{ t('tech.arch.server') }}</div>
              <div class="text-sm text-orange-300">{{ t('tech.arch.serverDesc') }}</div>
            </div>
            <div>
              <div class="text-3xl mb-2">🗄️</div>
              <div class="font-bold text-white">{{ t('tech.arch.data') }}</div>
              <div class="text-sm text-orange-300">{{ t('tech.arch.dataDesc') }}</div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Screenshots Section -->
    <section id="screenshots" class="py-28 bg-white dark:bg-navy-950 transition-colors duration-300">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16 section-fade">
          <div class="inline-block px-4 py-2 bg-orange-100 dark:bg-orange-900/30 rounded-full text-sm text-orange-600 dark:text-orange-400 font-semibold mb-4">{{ t('screenshots.badge') }}</div>
          <h2 class="text-4xl md:text-5xl font-extrabold text-navy-950 dark:text-white mb-4">{{ t('screenshots.title') }}</h2>
          <p class="text-xl text-gray-500 dark:text-gray-400 max-w-2xl mx-auto font-light">
            {{ t('screenshots.desc') }}
          </p>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="(screenshot, index) in screenshots" :key="index"
            class="screenshot-item bg-white dark:bg-navy-900 rounded-2xl overflow-hidden shadow-sm border border-gray-100 dark:border-gray-800 hover:shadow-xl transition-all duration-300 section-fade"
            :class="'delay-' + (index % 6 + 1)">
            <div class="aspect-video bg-orange-50 dark:bg-navy-800 flex items-center justify-center overflow-hidden">
              <img :src="base + 'images/' + screenshot.path" :alt="t(`screenshots.items[${index}].name`)"
                class="w-full h-full object-cover" loading="lazy" />
            </div>
            <div class="p-4 text-center font-semibold text-navy-950 dark:text-white">{{ t(`screenshots.items[${index}].name`) }}</div>
          </div>
        </div>
      </div>
    </section>

    <!-- Quick Start Section -->
    <section id="quickstart" class="py-28 bg-orange-50 dark:bg-navy-900 transition-colors duration-300">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16 section-fade">
          <div class="inline-block px-4 py-2 bg-orange-100 dark:bg-orange-900/30 rounded-full text-sm text-orange-600 dark:text-orange-400 font-semibold mb-4">{{ t('quickstart.badge') }}</div>
          <h2 class="text-4xl md:text-5xl font-extrabold text-navy-950 dark:text-white mb-4">{{ t('quickstart.title') }}</h2>
          <p class="text-xl text-gray-500 dark:text-gray-400 max-w-2xl mx-auto font-light">
            {{ t('quickstart.desc') }}
          </p>
        </div>
        <div class="max-w-4xl mx-auto">
          <div class="space-y-6">
            <div v-for="step in 4" :key="step" class="flex gap-5 items-start section-fade" :class="'delay-' + step">
              <div class="flex-shrink-0 w-12 h-12 gradient-bg rounded-2xl flex items-center justify-center text-white font-bold text-lg shadow-lg shadow-orange-200/50">{{ step }}</div>
              <div class="flex-1">
                <h3 class="text-xl font-bold text-navy-950 dark:text-white mb-2">{{ t(`quickstart.steps[${step - 1}].title`) }}</h3>
                <div class="bg-navy-950 dark:bg-navy-800 text-orange-300 p-4 rounded-2xl font-mono text-sm overflow-x-auto whitespace-pre-line">
                  {{ t(`quickstart.steps[${step - 1}].code`) }}
                </div>
              </div>
            </div>
          </div>
          <div class="mt-12 text-center section-fade delay-5">
            <p class="text-gray-500 dark:text-gray-400 mb-4">{{ t('quickstart.swagger') }}</p>
            <div class="inline-block bg-white dark:bg-navy-800 px-6 py-3 rounded-full font-mono text-navy-950 dark:text-white border border-gray-200 dark:border-gray-700 shadow-sm">
              http://localhost:8080/docs.html
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="py-28 bg-navy-950 dark:bg-navy-900 relative overflow-hidden transition-colors duration-300">
      <div class="absolute inset-0 opacity-20">
        <div class="absolute top-0 left-0 w-96 h-96 bg-orange-500 rounded-full filter blur-3xl -translate-x-1/2 -translate-y-1/2"></div>
        <div class="absolute bottom-0 right-0 w-96 h-96 bg-orange-300 rounded-full filter blur-3xl translate-x-1/2 translate-y-1/2"></div>
      </div>
      <div class="max-w-4xl mx-auto px-4 text-center relative z-10 section-fade">
        <h2 class="text-4xl md:text-5xl font-extrabold text-white mb-6">{{ t('cta.title') }}</h2>
        <p class="text-xl mb-10 text-gray-300 font-light">
          {{ t('cta.desc') }}
        </p>
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
          <a href="https://github.com/Siborne/doupo-take-out" target="_blank"
            class="inline-flex items-center justify-center gap-2 px-8 py-4 gradient-bg text-white rounded-full font-bold text-lg hover:shadow-lg hover:shadow-orange-400/30 transition-all duration-300">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 16 16"><path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"/></svg>
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
    <footer class="bg-navy-950 text-gray-400 py-12 border-t border-gray-800 transition-colors duration-300">
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
                <a href="#features" class="hover:text-orange-400 transition-colors">{{ t(`footer.items.features[${index}]`) }}</a>
              </li>
            </ul>
          </div>
          <div>
            <h4 class="text-white font-semibold mb-4">{{ t('footer.tech') }}</h4>
            <ul class="space-y-2.5 text-sm">
              <li v-for="(item, index) in 3" :key="index">
                <a href="#tech" class="hover:text-orange-400 transition-colors">{{ t(`footer.items.tech[${index}]`) }}</a>
              </li>
            </ul>
          </div>
          <div>
            <h4 class="text-white font-semibold mb-4">{{ t('footer.resources') }}</h4>
            <ul class="space-y-2.5 text-sm">
              <li v-for="(item, index) in 3" :key="index">
                <a :href="index === 0 ? 'https://github.com/Siborne/doupo-take-out' : index === 1 ? '#quickstart' : '#screenshots'" 
                   :target="index === 0 ? '_blank' : undefined"
                   class="inline-flex items-center gap-1.5 hover:text-orange-400 transition-colors">
                  <svg v-if="index === 0" class="w-4 h-4" fill="currentColor" viewBox="0 0 16 16"><path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"/></svg>
                  {{ t(`footer.items.resources[${index}]`) }}
                </a>
              </li>
            </ul>
          </div>
        </div>
        <div class="border-t border-gray-800 mt-8 pt-8 text-center text-sm">
          <p>© 2024 斗破快送. Built with ❤️</p>
        </div>
      </div>
    </footer>
  </div>
</template>
