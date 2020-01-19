function removeClassName(element, targetClassName) {
	let indexToRemove = element.className.indexOf(targetClassName);
	if (indexToRemove > 0) {
		element.className = element.className.replace(' ' + targetClassName, '');
	} else if (indexToRemove == 0) {
		element.className = element.className.replace(targetClassName, '').trim();
	}
}

function addClassName(element, targetClassName) {
	if(element.className.indexOf(targetClassName) > -1) { 
		return;
	}

	if (element.className.length > 0) {
		targetClassName = ' ' + targetClassName;
	}
	element.className += targetClassName;
}
