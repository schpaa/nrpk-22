const colors = require('tailwindcss/colors')

module.exports = {
    content: [
    "./public/eykt/index.html",
    "./public/eykt/js/cljs-runtime/**/*.js",
    "./public/eykt/**/*.{html,js}",
    "./lib/**/*.{cljs,js}"
    ],
    darkMode: 'class',
  theme: {
    extend: {},
    colors: {
        alt: {
            text: 'white',
            '300': '#DBFFB0',
            '400': '#A9E87E',
            '500': '#68A73D',
            DEFAULT: '#68A73D',
            'inverse': 'white',
            'text-inverse': '#68A73D',
            '600': '#145C3E',
            '700': '#145C3E',
        },
        transparent: 'transparent',
        current: 'currentColor',
        black: colors.black,
        yellow: colors.yellow,
        white: colors.white,
        gray: colors.gray,
        emerald: colors.emerald,
        indigo: colors.indigo,
        amber: colors.amber,
        sky: colors.sky,
        pink: colors.pink,
        red: colors.red
    },
    screens: {
        xs: '415px',
        br: '455px',
        mob: '640px',
        md: '768px',
        lg: '1024px',
        'print': {'raw':'print'},
        'screen': {'raw':'screen'},
    },
    zIndex: {
        auto: 'auto',
        0: '0',
        10: '10',
        20: '20',
        30: '30',
        40: '40',
        50: '50',
        60: '60',
        70: '70',
        100:'100',
        110:'110',
        200:'200',
        300:'300',
    },

  },
  plugins: [
      require('@tailwindcss/forms'),
  ],
}
