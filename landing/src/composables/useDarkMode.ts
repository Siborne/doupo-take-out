import { ref, watchEffect } from 'vue'

const isDark = ref(false)

function applyDark(dark: boolean) {
  if (dark) {
    document.documentElement.classList.add('dark')
  } else {
    document.documentElement.classList.remove('dark')
  }
}

export function useDarkMode() {
  const saved = localStorage.getItem('darkMode')
  if (saved === 'true') {
    isDark.value = true
    applyDark(true)
  } else if (saved === null && window.matchMedia('(prefers-color-scheme: dark)').matches) {
    isDark.value = true
    applyDark(true)
  }

  watchEffect(() => {
    applyDark(isDark.value)
    localStorage.setItem('darkMode', isDark.value ? 'true' : 'false')
  })

  const toggle = () => {
    isDark.value = !isDark.value
  }

  return {
    isDark,
    toggle
  }
}
