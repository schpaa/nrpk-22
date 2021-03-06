const colors = require('tailwindcss/colors')

module.exports = {
    content: [
//    "./public/booking/index.html",
//    "./public/booking/js/cljs-runtime/**/*.js",
    "./public/booking/**/*.{html,js}",
//    "./public/eykt/index.html",
//    "./public/eykt/js/cljs-runtime/**/*.js",
//    "./public/eykt/**/*.{html,js}",
//    "./lib/**/*.{cljs,js}",
    "./public/devcards/**/*.{html,js}",
//    "./public/devcards/js/cljs-runtime/**/*.js",
    ],
    darkMode: 'class',
  theme: {
    extend: {
        animation: {
            'bounce': 'bounce 10000ms linear infinite',
            'hbounce': 'hbounce 2000ms linear infinite',
            'pulse': 'pulse 2000ms linear infinite',
            'blink': 'blink 1000ms steps(2) infinite',
        	'slow-ping': 'slow-ping 2s cubic-bezier(0, 0, 0.2, 1) infinite',
        },
        keyframes: {
            bounce: {
                '0%, 100%': {
                    transform: 'translateY(0%)',

                    animationTimingFunction: 'cubic-bezier(0,0,0.2,1)',
                },
                '50%': {
                    transform: 'translateY(-25%)',
                    animationTimingFunction: 'cubic-bezier(0.8,0,1,1)',
                },
            },
            hbounce: {
                '0%': {
                    transform: 'translateX(0%)',
                    animationTimingFunction: 'cubic-bezier(0.8,0,1,1)',
                },
                '50%': {
                    transform: 'translateX(-25%)',
                    animationTimingFunction: 'cubic-bezier(0,0,0.2,1)',
                },
            },
            'blink': {
                '0%': {
                    opacity: '0.3',
                },
                '50%': {
                    opacity: '0',
                }
            },
            'pulse': {
                '0%': {
                    opacity: '0'
                },
                '50%': {
                    opacity: '0',
                },
                '51%': {
                    opacity: '1',
                },
                '100%': {
                    opacity: '1',
                },
            },

             'slow-ping': {
                  '0%': {
                    opacity: '1'
                  },
                  '75%, 100%': {
                    transform: 'scale(2)',
                    opacity: '0'
                  }
                }
            },
    },
    colors: {
        brand1: {DEFAULT: "var(--brand1)"},
        alt: {
            DEFAULT: '#68A73D', 
        },
        transparent: 'transparent',
        black: colors.black,
        white: colors.white,
        gray: colors.stone,
        amber: colors.amber,
        blue: colors.blue
    },
    screens: {
        xs: '412px',
        sm: '512px',
        md: '768px',
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
