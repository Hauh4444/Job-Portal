import 'dotenv/config';

export default {
    expo: {
        name: 'JobPortalApp',
        slug: 'jobportal',
        version: '1.0.0',
        extra: {
            BACKEND_API_URL: process.env.BACKEND_API_URL,
        },
    },
};
