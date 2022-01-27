const colors = require('tailwindcss/colors')

module.exports = {
    content: [
    "./public/eykt/index.html",
    "./public/eykt/js/cljs-runtime/**/*.js",
    "./public/eykt/**/*.{html,js}",
    "./lib/**/*.{cljs,js}",
    "./public/devcards/**/*.{html,js}",
    "./public/devcards/js/cljs-runtime/**/*.js",
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
        green: colors.lime,
        white: colors.white,
        gray: colors.stone,
//         slate stone sinc neutral gray


        emerald: colors.emerald,
        indigo: colors.indigo,
        amber: colors.amber,
        sky: colors.sky,
        orange: colors.orange,
        rose:colors.rose,
        pink: colors.pink,
        red: colors.red
    },
    screens: {
        xs: '415px',
        br: '455px',
        mob: '768px',
        /*md: '768px',*/
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
        400:'400',
        410:'410',
    },
    fontFamily: {
        script: [
            'Oleo Script Swash Caps',
            'cursive'
        ],
        sans: [
            'Inter',
            'ui-sans-serif',
            'system-ui',
            '-apple-system',
            'BlinkMacSystemFont',
            '"Segoe UI"',
            'Roboto',
            '"Helvetica Neue"',
            'Arial',
            '"Noto Sans"',
            'sans-serif',
            '"Apple Color Emoji"',
            '"Segoe UI Emoji"',
            '"Segoe UI Symbol"',
            '"Noto Color Emoji"',
        ],
        serif: ['Lora','Calluna Regular','Georgia','ui-serif', 'Georgia', 'Cambria', '"Times New Roman"', 'Times', 'serif'],
        mono: [
            'IBM Plex Mono',
            'ui-monospace',
            'SFMono-Regular',
            'Menlo',
            'Monaco',
            'Consolas',
            '"Liberation Mono"',
            '"Courier New"',
            'monospace',
        ],
    },

  },
  plugins: [
      require('@tailwindcss/forms')({strategy: 'class'}),
      require('@tailwindcss/typography'),
      require('@tailwindcss/line-clamp'),
  ],
}
