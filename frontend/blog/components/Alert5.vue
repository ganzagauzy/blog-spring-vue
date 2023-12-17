<script lang="ts" setup>
import { computed, reactive, ref } from "vue";
export type IStyles = "primary" | "success" | "warning" | "danger";

// props
const props = defineProps({
  title: {
    type: String,
    default: undefined,
  },
  text: {
    type: String,
    default: undefined,
  },
  type: {
    type: String,
    default: "primary",
  },
});

// styles
const styles = reactive<{
  [key: string]: string;
}>({
  primary: "",
  success:
    "dark:from-green-500/50 via-gray-200 to-gray-200 dark:via-slate-800 dark:to-slate-800",
  warning:
    "dark:from-green-500/50 via-gray-200 to-gray-200 dark:via-slate-800 dark:to-slate-800",
  danger:
    "dark:from-red-500/50 via-gray-200 to-gray-200 dark:via-slate-800 dark:to-slate-800",
});
const textStyles = reactive<{
  [key: string]: string;
}>({
  primary: "text-black dark:text-white",
  success: "text-green-500",
  warning: "text-green-500",
  danger: "text-red-500",
});

// selected
const isDestroyed = ref<Boolean>(false);
const selectedType = computed<IStyles>((): IStyles => {
  if (["primary", "success", "warning", "danger"].includes(props.type))
    return props.type as IStyles;
  return "primary";
});
const selectedStyle = computed(() => styles[selectedType.value]);
const selectedTextStyle = computed(() => textStyles[selectedType.value]);

// actions
const close = () => {
  isDestroyed.value = true;
};
</script>

<template>
  <div>
    <TransitionRoot :show="!isDestroyed" appear>
      <TransitionChild
        as="template"
        enter="duration-300 ease-out"
        enter-from="opacity-0"
        enter-to="opacity-100"
        leave="duration-300 ease-in"
        leave-from="opacity-100"
        leave-to="opacity-0"
      >
        <div
          :class="`bg-gray-200 dark:bg-slate-800 bg-gradient-to-r shadow-white/50 dark:shadow-slate-900/50 px-6 py-3 rounded-md shadow-lg flex space-x-6 ${selectedStyle}`"
        >
          <div class="flex items-center justify-center">
            <slot name="icon">
              <IconMdi:checkCircle
                v-if="selectedType === 'success'"
                :class="`text-2xl ${selectedTextStyle}`"
              />
              <!-- <icon-clarity:times-circle-solid
                                v-if="selectedType === 'danger'"
                                :class="`text-2xl ${selectedTextStyle}`"
                            /> -->
              <svg
                v-if="selectedType === 'danger'"
                class="w-6 h-6 text-red dark:text-white"
                :class="`text-2xl ${selectedTextStyle}`"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="currentColor"
                viewBox="0 0 20 20"
              >
                <path
                  d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 11.793a1 1 0 1 1-1.414 1.414L10 11.414l-2.293 2.293a1 1 0 0 1-1.414-1.414L8.586 10 6.293 7.707a1 1 0 0 1 1.414-1.414L10 8.586l2.293-2.293a1 1 0 0 1 1.414 1.414L11.414 10l2.293 2.293Z"
                />
              </svg>

              <icon-bi:exclamation-circle-fill
                v-if="selectedType === 'warning'"
                :class="`text-2xl ${selectedTextStyle}`"
              />
            </slot>
          </div>
          <div class="flex-1">
            <div :class="`font-bold text-lg mb-0.5 ${selectedTextStyle}`">
              <slot name="title">{{ props.title }}</slot>
            </div>
            <div class="text-gray-700 dark:text-gray-100">
              <slot name="title">{{ props.text }}</slot>
            </div>
          </div>
          <div>
            <button
              class="text-slate-600 hover:text-red-500 dark:text-gray-400 font-bold"
              @click="close"
            >
              <icon-clarity:times-line />
            </button>
          </div>
        </div>
      </TransitionChild>
    </TransitionRoot>
  </div>
</template>
