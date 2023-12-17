import { watch, ref } from "vue";

// Function to get or set a value in local storage if available
const localStorageGetterSetter = (
  key: string,
  defaultValue: number | boolean
) => {
  if (typeof localStorage !== "undefined") {
    const storedValue = localStorage.getItem(key);
    if (storedValue !== null) {
      return JSON.parse(storedValue);
    } else {
      localStorage.setItem(key, JSON.stringify(defaultValue));
      return defaultValue;
    }
  } else {
    return defaultValue;
  }
};

export const useCounter = () => {
  const counter = ref(localStorageGetterSetter("counter", 0));

  watch(counter, () => {
    if (typeof localStorage !== "undefined") {
      localStorage.setItem("counter", JSON.stringify(counter.value));
    }
  });

  return counter;
};

export const useTheme = () => {
  const theme = ref(localStorageGetterSetter("theme", false));

  watch(theme, () => {
    if (typeof localStorage !== "undefined") {
      localStorage.setItem("theme", JSON.stringify(theme.value));
    }
  });

  return theme;
};
