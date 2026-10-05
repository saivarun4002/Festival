import { useState, useEffect } from 'react';

const useCountdown = (targetDate) => {
  const [days, setDays] = useState(0);
  const [hours, setHours] = useState(0);
  const [minutes, setMinutes] = useState(0);
  const [seconds, setSeconds] = useState(0);
  const [isComplete, setIsComplete] = useState(false);

  useEffect(() => {
    let intervalId;
    if (targetDate) {
      const now = new Date();
      const target = new Date(targetDate);
      
      if (target < now) {
        setIsComplete(true);
        return;
      }
      
      const diff = target - now;
      const daysDiff = Math.floor(diff / (1000 * 60 * 60 * 24));
      const hoursDiff = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      const minutesDiff = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
      const secondsDiff = Math.floor((diff % (1000 * 60)) / 1000);
      
      setDays(daysDiff);
      setHours(hoursDiff);
      setMinutes(minutesDiff);
      setSeconds(secondsDiff);
      
      intervalId = setInterval(() => {
        const now = new Date();
        const target = new Date(targetDate);
        
        if (target < now) {
          setIsComplete(true);
          clearInterval(intervalId);
          return;
        }
        
        const diff = target - now;
        const daysDiff = Math.floor(diff / (1000 * 60 * 60 * 24));
        const hoursDiff = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutesDiff = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
        const secondsDiff = Math.floor((diff % (1000 * 60)) / 1000);
        
        setDays(daysDiff);
        setHours(hoursDiff);
        setMinutes(minutesDiff);
        setSeconds(secondsDiff);
      }, 1000);
      
      return () => clearInterval(intervalId);
    } else {
      setDays(0);
      setHours(0);
      setMinutes(0);
      setSeconds(0);
      setIsComplete(false);
    }
    
    return () => clearInterval(intervalId);
  }, [targetDate]);

  return { days, hours, minutes, seconds, isComplete };
};

export default useCountdown;